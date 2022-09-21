package jogo2048;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Container extends JFrame {
	public int w = 600;
	public int h = 600;
	public JPanel painelCentral;
	private JPanel telaInicial;

	public Container() {
		this.setTitle("2048");
		this.setSize(w, h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fechar quando clicar no X
		this.setLocationRelativeTo(null);

		this.painelCentral = new JPanel();
		BorderLayout l = new BorderLayout();
		this.painelCentral.setLayout(l);
		this.getContentPane().add(this.painelCentral);
		this.telaInicial = new TelaInicial(this);
		this.painelCentral.add(telaInicial);
		this.setVisible(true);
	}

	public void trocaPainel(JPanel painel) {
		for (Component c : painelCentral.getComponents()) {
			c.setVisible(false);
			c.setEnabled(false);
			c.enableInputMethods(false);
		}

		painelCentral.add(painel);
		painel.requestFocus();
	}
	
	public void telaInicial(JPanel panel) {
		if (panel != null) {
			this.painelCentral.remove(panel);
		}
//		for (Component c : painelCentral.getComponents()) {
//			c.setVisible(false);
//			c.setEnabled(false);
//			c.enableInputMethods(false);
//		}
		this.telaInicial.requestFocus();
		this.telaInicial.setEnabled(true);
		this.telaInicial.setVisible(true);
		this.telaInicial.enableInputMethods(true);
	}

	public static void main(String[] args) {
		new Container();
	}
}
