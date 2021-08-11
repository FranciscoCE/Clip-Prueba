package com.example.clip1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Registro;
import com.example.repo.IRegistroRepo;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;





@RestController
public class ClipController {
	
	//private static final String template = "Hello, %s!";
	private final AtomicLong hits = new AtomicLong();
	
	 private final static String QUEUE_NAME = "clip_test";
		
	@Autowired
	IRegistroRepo iRegistroRepo;
	
	
		
	//@GetMapping("/add/hit")
	/*RequestParam se utilió méotodo para validar la cadena que no contenga nada que no
	 * sean letras ni números
	 * 
	 * */
	
	//Esta valida y solo acepta numeros y letras y trae caracteres diferentes, los desprecia
	@GetMapping("/hit/{name}")
	public ClipTO hit (@PathVariable String name){
	//public ClipTO hit(@RequestParam (value="name", defaultValue = "Clip") String name){
		//System.out.println("El name que llega "+name);
	long aux = 0;
	
	
	if(validaString(name)) {
		//System.out.println("El name true");
		long auxHits = hits.incrementAndGet();
		Registro s = new Registro();
		s.setHits(auxHits);
		s.setKey(name);		
		
		try {
			publisher(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ClipTO(auxHits, name);
	}else {
		System.out.println("El name false");
		return new ClipTO(aux, "Caracteres inválidos en el parámetro enviado");
		}
			
	}
	
	
	@GetMapping("/hit")
	public List<Registro> consumer() throws IOException, TimeoutException{
		

		List<Registro> listaRegistros = iRegistroRepo.findAll();
		
		ClipController c = new ClipController();
	
		publisherString(c.acomodaRabbitMQ(listaRegistros));	
		
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, 
                false, false,false, null);
        System.out.println("[!] ESPERANDO NUEVOS MENSAJES...");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[x] MENSAJE RECIBIDO: ' " + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    
        
		return iRegistroRepo.findAll();
	
	}
		
	
	private void publisher (Registro registro) throws IOException, TimeoutException{

		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicPublish("", QUEUE_NAME, null, registro.toString().getBytes("UTF-8"));
  
        iRegistroRepo.save(registro);
        System.out.println("[!] MENSAJE ENVIADO POR RABBITMQ: '" + registro.toString() + "'");
        channel.close();
        connection.close();
	}
	
	public void publisherString (String lista) throws IOException, TimeoutException{

		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicPublish("", QUEUE_NAME, null, lista.getBytes("UTF-8"));
        System.out.println("[!] MENSAJE ENVIADO POR RABBITMQ: '" + lista + "'");
        channel.close();
        connection.close();
	}
	

	
	private static boolean validaString(String cadena){
	boolean validaNumerico = true;
		for(int i=0; i<cadena.length(); i++){
			if((int)cadena.charAt(i)>=48 && (int)cadena.charAt(i)<=57 || (int)cadena.charAt(i)>=97 && (int)cadena.charAt(i)<=122 
							|| (int)cadena.charAt(i)>=65 && (int)cadena.charAt(i)<=90){
						//true
			}else{
			validaNumerico = false;
				}		
			}	
		return validaNumerico;
		}
	
	
	private String acomodaRabbitMQ(List<Registro> listaRegistros){
		
		String MensajeRabbitMq = "";
		
		for(int i=0; i<listaRegistros.size(); i++) {
			if(i==0) {
				MensajeRabbitMq += "[{"+(char)34+"hits"+(char)34+": "+listaRegistros.get(i).getHits()+", "+(char)34+"key"+(char)34+": "+(char)34+""+listaRegistros.get(i).getKey()+""+(char)34+"},";
			}if(i==listaRegistros.size()-1) {
				MensajeRabbitMq += "{"+(char)34+"hits"+(char)34+": "+listaRegistros.get(i).getHits()+", "+(char)34+"key"+(char)34+": "+(char)34+""+listaRegistros.get(i).getKey()+""+(char)34+"}]";
			}else {
				MensajeRabbitMq += "{"+(char)34+"hits"+(char)34+": "+listaRegistros.get(i).getHits()+", "+(char)34+"key"+(char)34+": "+(char)34+""+listaRegistros.get(i).getKey()+""+(char)34+"},";
			}
	
		}
		
		return MensajeRabbitMq;
	}
		
	
}
