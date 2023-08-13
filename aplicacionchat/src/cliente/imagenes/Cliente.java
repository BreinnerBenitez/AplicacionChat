package breinnersockects;

import java.io.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.*;
import javax.swing.*;

import java.net.*;
import java.util.ArrayList;

public class Cliente {
	
	//ESTA APLICACION LA REALICE, PARA APRENDER CONCEPTOS 
	//GRACIAS POR ESTAR AQUI ATT: BREINNER BENITEZ
	
	public static void main(String[] args) {

		
		
		MarcoCliente mimarco = new MarcoCliente();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MarcoCliente extends JFrame {

	
	
	
	public MarcoCliente() {

		setTitle("cliente_breinner(junior)chat :)");
		setBounds(100,100, 450, 450);

		
	
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		
		Image image= toolkit.getImage("src/cliente/imagenes/foto.png");
		
		setIconImage(image);

	
		
		
		LaminaMarcoCliente milamina = new LaminaMarcoCliente();
		add(milamina);
		setVisible(true);
		
		
		
		EnvioOnline enviaip = new EnvioOnline(); //ejecuta socket al abrir ventana
		
		addWindowListener(enviaip);
		
	
		
	}

}
// ENVIO DE SEÑAL VENTANA//
class EnvioOnline extends WindowAdapter {
	private String ipserver;
	

	public void windowOpened(WindowEvent e) {
		
		try {
		  	
			
			
			
			// IP DEL SERVIDOR	
			
			int contador = 0;
			do {
					
				ipserver= JOptionPane.showInputDialog(null,"confirma la ip del servidor: ","IP",2);
				
				
				//System.out.println(ipserver);
				
		
				
				
				
				//ME PASA IP PARA DEL SERVIDOR EN LA SEGUNDA VUELTA Y ENVIAR IP CLIENTES 
				
			//	System.out.print("segunda vueta prueba" );
				
				char arreglo[] = new char[ipserver.length()];

				for (int i = 0; i < arreglo.length; i++) {

					arreglo[i] = ipserver.charAt(i);

					if (arreglo[i] == '.') {

						contador++;

					}
				}

				
				if (!(contador == 3)) {

					contador = 0;

				}

			} while (!(contador == 3));
			
			// IP PARA SERVIDOR FINALIZA 
			
			System.out.print("prueba ");
			Socket sockectinforma = new Socket(ipserver,9999);
			
			Datos datos = new Datos ();
			
			datos.setMensaje("enlinea");
			
			ObjectOutputStream informaip_datos = new ObjectOutputStream(sockectinforma.getOutputStream());
			
			informaip_datos.writeObject(datos);
			
			informaip_datos.close();
			
			sockectinforma.close();
			
			
		}catch(Exception e2) {
			
			JOptionPane.showMessageDialog(null, "error, servidor.exe  no abierto","Error",2);
			
		}
		
	}
	// TERMINA  EL ENVIO DE  SEÑAL VENTANA//


}


class LaminaMarcoCliente extends JPanel implements Runnable {

 
	public LaminaMarcoCliente() {
		
	setLayout(new BorderLayout(10,10));
		//mensaje al inicio de la aplicacion
		
		String nombre= JOptionPane.showInputDialog(null,"Ingresa tu nombre por favor: ");
		 
		// panel norte inicia 
		JPanel panelnorte = new JPanel(); // 
		
		add(panelnorte,BorderLayout.NORTH);
		
		JLabel ipt = new JLabel("En linea: ");
		
		panelnorte.add(ipt);
		
		 ip = new JComboBox ();
		 
		/*ip.addItem("usaurio1");
		ip.addItem("usaurio2");  /ESTO ERA PARA LAS PUREBAS
		ip.addItem("usaurio3");*/
		//ip.addItem("192.168.20.20");
		 
		
		panelnorte.add(ip);
		
		
		
		JLabel texto = new JLabel("Nombre: ");
		
		panelnorte.add(texto);
		
		
		nick = new JLabel ();
		nick.setText(nombre);
		panelnorte.add(nick);
		
		// panel norte termina
		
		
		//parte del medio empieza 
		
		JPanel panelarea = new JPanel();

		recibiendo = new JTextArea(20, 20);
		
		JScrollPane scroll = new JScrollPane(recibiendo);

		add(panelarea);

		panelarea.add(scroll);
		
		//parte central termina
		
		
		
		//  empieza panel sur 
		
		JPanel panelsur = new JPanel ();
		
		add(panelsur,BorderLayout.SOUTH);
		
		campo1 = new JTextField(15);

		panelsur.add(campo1);

		miboton = new JButton("Enviar");

		panelsur.add(miboton);
		
		//ipservidor= new JTextField(11);
		
	//	panelsur.add(ipservidor);
		
		

		
		// termina panel sur 
		
	

		EnviaTexto evento = new EnviaTexto();

		miboton.addActionListener(evento);
		
		Thread hilo = new Thread(this);
		
		hilo.start(); 

	}

	private class EnviaTexto implements ActionListener  {
	
		
	 String ipserver= JOptionPane.showInputDialog(null,"ingresa la ip del servidor, no ingrese "
	 		+ "diferentes sino el mensaje a los clientes no llega: ","IP",2);

		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			
			// System.out.print(campo1.getText());
			
			 // me pasa ip en la segunda vuelta

			 recibiendo.append("\n "+campo1.getText());
			// System.out.println("ip en la primera vuelta "+ipserver);
			 
	           try {
				
				 
				Socket misocket = new Socket(ipserver, 9999);

				
				Datos datos = new Datos();
				
				datos.setNick(nick.getText());
				datos.setIp(ip.getSelectedItem().toString());
				datos.setMensaje(campo1.getText());
				
				ObjectOutputStream paquete_datos = new ObjectOutputStream (misocket.getOutputStream());
				
				paquete_datos.writeObject(datos);
				
				/*DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());

				flujo_salida.writeUTF(campo1.getText());

				flujo_salida.close();*/
				
				paquete_datos.close();

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.print(e1.getMessage());
			}

		}

		


	}

	private JTextField campo1;

	private JButton miboton;
	private JTextArea recibiendo;
	private JComboBox ip;
	private JLabel nick;
	//public JTextField ipservidor;
   
	
	

	@Override
	public void run() {

		try {

			
			
			
			ServerSocket servidor = new ServerSocket(9090);

			while (true) {

				Socket misocket = servidor.accept();
				
				Datos datosrecibido;
			
				ObjectInputStream recibedatos = new ObjectInputStream(misocket.getInputStream());
				
				datosrecibido= (Datos)recibedatos.readObject();
				
			
				
				String ipm,nombre,mensaje;
				
				ipm=datosrecibido.getIp();
				nombre=datosrecibido.getNick();
				mensaje=datosrecibido.getMensaje();
				
				if (!datosrecibido.getMensaje().equals("enlinea")) {
					
				recibiendo.append("\n te habla: "+nombre+"-- mensaje: "+mensaje);
				//recibiendo.append("\n"+datosrecibido.getIps());
				
				}else {
					
					
					ArrayList <String> IpsMenu = new ArrayList<String>();
					
					IpsMenu= datosrecibido.getIps();
					
					ip.removeAllItems();
					
					for (String z: IpsMenu) {
						
						ip.addItem(z);
						
					}
					
				}
				
				
				misocket.close();
				
				
				
				
			}
			
			
			
			
			

		} catch (IOException  |  ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// ACEPTA RESPUESTAS DEL SERVIDOR 
		/* try {
		
			 ServerSocket servidor = new ServerSocket(9997);
		
		    while(true) {
		    	
		    	
		    	Socket misocket = servidor.accept();
		    	
		    	DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());

				String mensaje_texto = flujo_entrada.readUTF();

				recibiendo.append("\n servidor escribe " + mensaje_texto);
				
			

				misocket.close();
		    	
		    	
		    	
		    }
		
		
		 }catch(Exception e) {
			 
			// System.out.print("error en cliente");
			 
		 } */
		 
		 // TERMINA REPUESTAS DEL SERVIDOR
		 
		
		
		
		
		 
	}
	
	
	
}


class Datos implements Serializable{  //  DATOS EN BYTES (binarios) 
	
	
   public Datos() {
			
	}
	
	
	private String nick,ip,mensaje;
	
	private ArrayList<String> ips;
	
	public ArrayList<String> getIps() {
		return ips;
	}

	public void setIps(ArrayList<String> ips) {
		this.ips = ips;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
	
}
