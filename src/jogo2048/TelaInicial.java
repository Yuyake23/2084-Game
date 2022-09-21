package jogo2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants; //jakarta

@SuppressWarnings("serial")
public class TelaInicial extends JPanel {
	public Container C;

	public TelaInicial(Container C) {
		this.C = C;
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(new GridLayout(2, 1));

		JLabel jl = new JLabel("2048", SwingConstants.CENTER);
		jl.setFont(new Font("Arial", Font.PLAIN, 200));
		this.add(jl, 0);
		
		JButton b = new JButton("Jogar");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				C.trocaPainel(new Partida(C, 4, 4));
			}
		});
		this.add(b, 1);
	}

}
