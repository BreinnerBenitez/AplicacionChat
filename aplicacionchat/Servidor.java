package breinnersockects;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.net.*;
public class Servidor {

	
	//ESTA APLICACION LA REALICE, PARA APRENDER CONCEPTOS 
		//GRACIAS POR ESTAR AQUI ATT: BREINNER BENITEZ
	
	public static void main(String [] breinenr) {
	MarcoServidor mimarco=new MarcoServidor();
	
	mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	
	}
}
class MarcoServidor extends JFrame implements Runnable {
	
	
	private	JTextArea areatexto;
	private JLabel responde;
	private JTextField texto;
	JButton boton;

	public MarcoServidor(){
		
		setTitle("servidor-Breinner Junior :)");
		setBounds(600,200,280,350);		
		
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		
		Image image= toolkit.getImage("src/servidor/imagenes/foto.png");
		
		setIconImage(image);
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		JPanel laminaresponde = new JPanel();
		
		laminaresponde.setLayout(new BorderLayout());
		
		 responde = new JLabel("reponde-servidor");
		// texto = new JTextField(10);   // estas linea de codigo es para que el servidor responda 
		// boton = new JButton("enviar");
	
		
	//	laminaresponde.add(texto,BorderLayout.WEST);
	//	laminaresponde.add(boton);
		laminaresponde.add(responde,BorderLayout.NORTH);
		add(laminaresponde,BorderLayout.NORTH);
		
		
		
		
		areatexto=new JTextArea(10,10);
		
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina, BorderLayout.CENTER);
		
		setVisible(true);
		
		Thread mihilo = new Thread(this);
		
		mihilo.start();
		
	//	EventoServidor eventos =  new EventoServidor(); // este es el evento para que el servidor pueda responder
		
	//	boton.addActionListener(eventos);
		
		
		}
	
	
	@Override
	public void run() {
	
		try {
			
			
			
				
			ServerSocket servidor = new ServerSocket (9999); // ACEPTA CONEXIONES PARA REENVIAR
			
			String nick,ip,mensaje;
			
			ArrayList <String> listaip = new ArrayList <String>(); // me guardara todas las ip
			
			Datos datos_recibido; // esta clase viene de cliente
			
			while (true) {
				
			Socket misocket = servidor.accept();
		
			
			ObjectInputStream paquete_Datos= new ObjectInputStream(misocket.getInputStream());
			
			datos_recibido=(Datos) paquete_Datos.readObject();
			
			nick= datos_recibido.getNick();
			ip=datos_recibido.getIp();
			mensaje=datos_recibido.getMensaje();
			
			if (!mensaje.equals("enlinea")) {
			
			areatexto.append("\n"+nick+": "+mensaje+" - para la ip: "+ ip);
			paquete_Datos.close();
			misocket.close();
			
			
		/*	DataInputStream flujo_entrada= new DataInputStream(misocket.getInputStream());
			
			String mensaje_texto=flujo_entrada.readUTF();
			
			areatexto.append("\n"+ mensaje_texto);
			
			misocket.close();*/
			
			
			
			Socket enviaDestinatario = new Socket(ip,9090); // RENVIA DATOS A LA APLICACION  CLIENTE DE LA IP SEÑALADA EN EL COMBOBOX DEL  
															//DEL CLIENTE ORIGEN 
			ObjectOutputStream paquete_reenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
			
			paquete_reenvio.writeObject(datos_recibido);
			
			paquete_reenvio.close();
			enviaDestinatario.close();
			
			
			
			}else {
			
				// DETECTA LA IP DEL CLIENTE //
				
			    InetAddress localizacion= misocket.getInetAddress();
				
				String ipremota =localizacion.getHostAddress();
				
			//	System.out.println("ip"+ipremota); PRUEBA QUE TOME LAS IP REMOTA  
				
				listaip.add(ipremota);  // AGREGA IP EN LA LISTA 
				
				datos_recibido.setIps(listaip); // 
				
				 for (String j:listaip) {
					 
					 
					 
					 	Socket enviaDestinatario = new Socket( j,9090); // ENVIA  IPS AL CLIENTE PARA EN EL CLIENTE AGREGAR LAS IP EN LINEA
						
						ObjectOutputStream paquete_reenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
						
						paquete_reenvio.writeObject(datos_recibido);
						System.out.println(j);
						
					//	EventoServidor llevarip = new EventoServidor(); // LLEVA IP PARA QUE SERVIDOR ENVIE MENSAJES
						
				  //		llevarip.setTraerip(j);
						
						paquete_reenvio.close();
						enviaDestinatario.close();
					 
				 }
				
				///////////////////////////////////////
				
			}
			
			
			
			
			
			
			}
			
		} catch (IOException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// esta clase privada es para que el servidor tambien pueda enviar datos 
    // debes configurar en el cliente el hilo para aceptar las solicitudes 
	
/*
private class EventoServidor implements ActionListener{

 private  String traerip; 	
	
 
       
	
 public String getTraerip() {
		return traerip;
	}

	public void setTraerip(String traerip) {
		this.traerip = traerip;
	}
	
 
 
 
 @Override
	public void actionPerformed(ActionEvent e) {
		

		
		
		try {
			Socket misocket = new Socket(getTraerip(),9997); // 
			
			DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
			
			flujo_salida.writeUTF(texto.getText());
			
			flujo_salida.close();
			
			
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.print(e1.getMessage());
		}
		
		
	} 

	
	
	
	
}*/
	


	
	
	
	
}

