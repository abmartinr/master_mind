package mastermind;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MasterMind extends JFrame {

	Pelota p_amarillo, p_celeste, p_lila, p_marino, p_marron, p_plata, p_rojo,
			p_verde, p_blanco_m, p_negro_m;
	String[] cadena = new String[4];
	String colorSeleccionado;
	JButton jugar;
	boolean victoria = false;

	Map<Integer, ArrayList<Pelota>> listaLineas = new HashMap<>();
	Map<Integer, ArrayList<Pelota>> listaLineasRes = new HashMap<>();
	int currentRow = 10;

	// Contador para saber si están los 4 colores puestos para habilitar el
	// botón de OK
	int contLinea = 0;

	public MasterMind() {

		setLayout(new BorderLayout());
		setResizable(false);
		setContentPane(new JLabel(new ImageIcon(getClass().getResource(
				"fondo.png"))));
		setLayout(new BorderLayout(0, 0));
		add(new PanelJugadas(), BorderLayout.WEST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);

		add(new PanelPelotas(), BorderLayout.SOUTH);
		crearCadena();
		add(new PanelResultado(), BorderLayout.EAST);

	}

	class PanelJugadas extends JPanel {

		public PanelJugadas() {
			setLayout(new GridLayout(10, 4, 10, 0));
			setOpaque(false);
			for (int i = 1; i < 11; i++) {
				ArrayList<Pelota> linea = new ArrayList<Pelota>();
				for (int j = 0; j < 4; j++) {
					Pelota p = new Pelota("empty", 1);
					p.addActionListener(emptyButton);
					if (currentRow == i) {
						p.setEnabled(true);
					} else {
						p.setEnabled(false);
					}
					add(p);
					linea.add(p);
				}
				listaLineas.put(i, linea);
			}
		}

	}

	class PanelResultado extends JPanel {
		public PanelResultado() {
			setPreferredSize(new Dimension(60, 540));
			setLayout(new GridLayout(10, 4, 5, 30));
			setOpaque(false);
			for (int i = 1; i < 11; i++) {
				ArrayList<Pelota> linea = new ArrayList<Pelota>();

				for (int j = 0; j < 4; j++) {
					Pelota p = new Pelota(1);
					add(p);
					linea.add(p);
				}
				listaLineasRes.put(i, linea);
			}
		}
	}

	class PanelPelotas extends JPanel {
		public PanelPelotas() {
			setLayout(new GridLayout(1, 9, 0, 0));
			setOpaque(false);
			p_amarillo = new Pelota("amarillo", 1);
			p_amarillo.addActionListener(colorButton);
			p_celeste = new Pelota("celeste", 1);
			p_celeste.addActionListener(colorButton);
			p_lila = new Pelota("lila", 1);
			p_lila.addActionListener(colorButton);
			p_marino = new Pelota("marino", 1);
			p_marino.addActionListener(colorButton);
			p_marron = new Pelota("marron", 1);
			p_marron.addActionListener(colorButton);
			p_plata = new Pelota("plata", 1);
			p_plata.addActionListener(colorButton);
			p_rojo = new Pelota("rojo", 1);
			p_rojo.addActionListener(colorButton);
			p_verde = new Pelota("verde", 1);
			p_verde.addActionListener(colorButton);

			add(p_amarillo);
			add(p_celeste);
			add(p_lila);
			add(p_marino);
			add(p_marron);
			add(p_plata);
			add(p_verde);
			add(p_rojo);

			jugar = new JButton("OK");
			jugar.setEnabled(false);
			jugar.setPreferredSize(new Dimension(80, 40));
			jugar.addActionListener(okButton);
			add(jugar);

		}
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MasterMind frame = new MasterMind();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private ActionListener okButton = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			jugar.setEnabled(false);
			comparaColoresLinea();
			currentRow--;
			contLinea = 0;
			colorSeleccionado = null;

			if (victoria == true) {

				JOptionPane.showMessageDialog(null,
						"¡ENHORABUENA! Has adivinado la combinación");
				reiniciar();

			} else {
				if (currentRow == 0) {
					JOptionPane
							.showMessageDialog(null,
									"¡Lástima! No has adivinado la combinación correcta");
					reiniciar();
				} else {
					activarLinea();
					desactivarLinea();
				}
			}

		}
	};

	private ActionListener colorButton = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Pelota p = (Pelota) arg0.getSource();
			// Asigno el color de la pelota pulsada a ColorSeleccionado
			colorSeleccionado = p.getColor();

		}
	};

	// Listener de los botones vacíos.
	private ActionListener emptyButton = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			// Asigno la pelota que he pulsado a p
			Pelota p = (Pelota) arg0.getSource();

			// Esta lista contiene la linea actual en la que estoy jugando los
			// colores puestos
			ArrayList<Pelota> listaactual = listaLineas.get(currentRow);

			boolean checkyaesta = false;
			for (Pelota p2 : listaactual) {
				// Si ese color que tengo en ColorSeleccionado ya está en la
				// lista, es porque lo he puesto y entonces marco el checkyaesta
				// como true
				if (p2.getColor() == colorSeleccionado) {
					checkyaesta = true;
				}
			}
			// Si el botón pulsado está vacío y el color no está entonces
			// entraré en este if
			if (p.getColor() == "empty" && checkyaesta != true) {
				if (colorSeleccionado != null) {
					// Si el color seleccionado es alguno (diferente a nulo)
					// Le pongo ese colorseleccionado al botón pulsado y
					// reinicio Colorseleccionado
					p.setIcon(colorSeleccionado);
					colorSeleccionado = null;
					contLinea++;
				}

			} else if (p.getColor() != "empty") {
				// Si el botón pulsado ya tiene un color asignado, le reasigno
				// el vacío y quito uno al contLinea
				p.setIcon("empty");
				contLinea--;
			}

			if (contLinea == 4) {
				// Si está el contLinea a 4 activo el boton OK
				jugar.setEnabled(true);
			} else {
				// De lo contrario lo desactivo
				jugar.setEnabled(false);
			}
		}
	};

	// Creo la combinación de colores sacándolo de una manera aleatoria de
	// la "chistera" colores
	public void crearCadena() {

		ArrayList<String> colores = new ArrayList<String>(Arrays.asList(
				"amarillo", "celeste", "lila", "marino", "marron", "plata",
				"rojo", "verde"));
		Collections.shuffle(colores);
		Random rand = new Random();

		int num;

		for (int i = 0; i < 4; i++) {
			num = rand.nextInt(colores.size());
			cadena[i] = colores.get(num);
			colores.remove(num);
		}

		// Muestro la cadena generada (Para saber si funciona bien)
		for (int i = 0; i < 4; i++) {

			System.out.println(cadena[i]);
		}
	}

	// Metodo en el que comparo la linea puesta con la combinación generada
	public void comparaColoresLinea() {
		int contcheckblancas = 0;
		int contchecknegras = 0;

		// Miro cuantas pelotas hay bien
		for (int i = 0; i < 4; i++) {

			for (Pelota p : listaLineas.get(currentRow)) {
				if (cadena[i] == (p.getColor())
						&& i == listaLineas.get(currentRow).indexOf(p)) {
					// Si está bien y en buena posición, añado una blanca
					contcheckblancas++;

				} else if (cadena[i] == (p.getColor())
						&& i != listaLineas.get(currentRow).indexOf(p)) {
					// Si está bien y en mala posición, añado una negra
					contchecknegras++;
				}
			}
		}
		// Busco cual es la linea de resultado actual y me la guardo en
		// listalineasresactual.
		ArrayList<Pelota> listaLineasResActual = listaLineasRes.get(currentRow);

		// Contador para el indice de la linea
		int j = 0;
		if (contcheckblancas > 0 || contchecknegras > 0) {
			for (int i = 0; i < contcheckblancas; i++) {
				// Hago visibles tantas blancas como blancas haya
				listaLineasResActual.get(j).setIcon("blanco_m");
				listaLineasResActual.get(j).setVisible(true);
				j++;
			}
			for (int i = 0; i < contchecknegras; i++) {
				// Hago visibles tantas negras como negras haya
				listaLineasResActual.get(j).setIcon("negro_m");
				listaLineasResActual.get(j).setVisible(true);
				j++;
			}

		}

		if (contcheckblancas == 4) {
			// Si cuenta 4 blancas, marco victoria como verdadero.
			victoria = true;
		}

	}

	// Activo la linea para poder jugar en ella.
	public void activarLinea() {

		if (currentRow > 0) {
			for (Pelota p : listaLineas.get(currentRow)) {
				p.setEnabled(true);
			}
		}
	}

	// Desactivo la linea anterior para no poder modificarla.
	public void desactivarLinea() {
		for (Pelota p : listaLineas.get(currentRow + 1)) {
			p.setEnabled(false);
		}
	}

	public void reiniciar() {
		currentRow = 10;
		victoria = false;
		for (int i = 1; i < 11; i++) {
			ArrayList<Pelota> aux = listaLineas.get(i);
			for (Pelota p : aux) {
				p.setIcon("empty");
				p.setDisabledIcon(null);
				p.setEnabled(false);
			}

		}
		activarLinea();
		for (int i = 1; i < 11; i++) {
			ArrayList<Pelota> auxR = listaLineasRes.get(i);
			for (Pelota pR : auxR) {
				pR.setVisible(false);
			}

		}
		crearCadena();
	}

}
