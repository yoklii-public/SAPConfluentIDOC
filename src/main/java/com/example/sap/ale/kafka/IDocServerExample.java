package com.example.sap.ale.kafka;

import com.sap.conn.idoc.*;

import java.io.*;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.sap.conn.jco.server.*;
import com.sap.conn.idoc.jco.*;

public class IDocServerExample {
	private static IDocXMLProcessor XML_PROCESSOR = JCoIDoc.getIDocFactory().getIDocXMLProcessor();

	public static void main(String[] a) {
		try {
			// see provided examples of configuration files MYSERVER.jcoServer and
			// S4H.jcoDestination
			JCoIDocServer server = JCoIDoc.getServer("MYSERVER");
			server.setIDocHandlerFactory(new MyIDocHandlerFactory());
			server.setTIDHandler(new MyTidHandler());

			MyThrowableListener listener = new MyThrowableListener();
			server.addServerErrorListener(listener);
			server.addServerExceptionListener(listener);
			server.setConnectionCount(1);
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class MyIDocHandler implements JCoIDocHandler {

		private String idocAsXml(IDocDocument doc) {
			StringWriter writer = new StringWriter();
			try {
				XML_PROCESSOR.render(doc, writer, IDocXMLProcessor.RENDER_WITH_TABS_AND_CRLF);
			} catch (IOException e) {
				throw new RuntimeException("Unexpected IO error: " + e.getMessage(), e);
			}
			writer.flush();
			return writer.toString();
		}

		public void handleRequest(JCoServerContext serverCtx, IDocDocumentList idocList) {

			IDocDocumentIterator iterator = idocList.iterator();
			try (Producer<String, String> producer = KafkaFactory.createProducer()) {
				String keyBase = serverCtx.getTID();
				int keyIndex = 1;
				while (iterator.hasNext()) {
					String message = idocAsXml(iterator.next());
					String key = keyBase + keyIndex++;
					producer.send(new ProducerRecord<>(KafkaFactory.TOPIC, key, message)).get();
					producer.flush();
					System.out.println("Sent to Kafka: " + key);
				}
			} catch (InterruptedException e) {
				System.err.println("Message send interrupted: " + e.getMessage());
				e.printStackTrace();
			} catch (ExecutionException e) {
				System.err.println("Failed to send: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	static class MyIDocHandlerFactory implements JCoIDocHandlerFactory {
		private JCoIDocHandler handler = new MyIDocHandler();

		public JCoIDocHandler getIDocHandler(JCoIDocServerContext serverCtx) {
			return handler;
		}
	}

	static class MyThrowableListener implements JCoServerErrorListener, JCoServerExceptionListener {

		public void serverErrorOccurred(JCoServer server, String connectionId, JCoServerContextInfo ctx, Error error) {
			System.out.println(">>> Error occured on " + server.getProgramID() + " connection " + connectionId);
			error.printStackTrace();
		}

		public void serverExceptionOccurred(JCoServer server, String connectionId, JCoServerContextInfo ctx,
				Exception error) {
			System.out.println(">>> Error occured on " + server.getProgramID() + " connection " + connectionId);
			error.printStackTrace();
		}
	}

	static class MyTidHandler implements JCoServerTIDHandler {
		public boolean checkTID(JCoServerContext serverCtx, String tid) {
			System.out.println("checkTID called for TID=" + tid);
			return true;
		}

		public void confirmTID(JCoServerContext serverCtx, String tid) {
			System.out.println("confirmTID called for TID=" + tid);
		}

		public void commit(JCoServerContext serverCtx, String tid) {
			System.out.println("commit called for TID=" + tid);
		}

		public void rollback(JCoServerContext serverCtx, String tid) {
			System.out.print("rollback called for TID=" + tid);
		}
	}
}
