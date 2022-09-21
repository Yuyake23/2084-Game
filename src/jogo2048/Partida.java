package jogo2048;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Partida extends JPanel implements ActionListener {

	private static final int CIMA = 1;
	private static final int DIRETA = 2;
	private static final int BAIXO = 3;
	private static final int ESQUERDA = 4;

	JPanel[][] mileStones;
	Map<String, Color> cores;
	Random r;
	Random r2 = new Random();
	Container C;

	public Partida(Container C, int qtdLinha, int qtdColuna) {
		this.C = C;
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		this.setBackground(new Color(192, 192, 192));
		this.addKeyListener(new TecladoAdapter());
		this.setLayout(new GridLayout(qtdLinha, qtdColuna, 10, 10));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.cores = configuraCores();
		this.mileStones = inicializaMileStones(qtdLinha, qtdColuna);
	}

	private JPanel[][] inicializaMileStones(int qtdLinha, int qtdColuna) {
		Font mileStonesFont = new Font("Arial", Font.PLAIN, 80);
		JPanel[][] mileStones = new JPanel[qtdLinha][qtdColuna];
		for (int i = 0; i < mileStones.length; i++) {
			for (int j = 0; j < mileStones[i].length; j++) {
				mileStones[i][j] = new JPanel();
				mileStones[i][j].setBackground(cores.get(" "));
				mileStones[i][j].setLayout(new BorderLayout());

				JLabel jl = new JLabel(" ", SwingConstants.CENTER);
				jl.setFont(mileStonesFont);
				mileStones[i][j].add(jl);

				this.add(mileStones[i][j]);
			}
		}

		gerarNovaMileStone();
		gerarNovaMileStone();

		return mileStones;
	}

	private HashMap<String, Color> configuraCores() {
		HashMap<String, Color> cores = new HashMap<>();
		cores.put(" ", new Color(150, 150, 150));
		for (int i = 2; i != 2048; i *= 2) {
			r = new Random(i / 3);
			cores.put(String.valueOf(i), new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
		}
		return cores;
	}

	private class TecladoAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int kc = e.getKeyCode();

			if (kc == KeyEvent.VK_RIGHT) {
				mover(DIRETA);
			} else if (kc == KeyEvent.VK_DOWN) {
				mover(BAIXO);
			} else if (kc == KeyEvent.VK_LEFT) {
				mover(ESQUERDA);
			} else if (kc == KeyEvent.VK_UP) {
				mover(CIMA);
			}
		}
	}

	private void mover(int direcao) {
		boolean moveu = false;
		if (direcao == DIRETA) {
			for (int i = 0, lim; i < mileStones.length; i++) {
				lim = mileStones[i].length - 1;
				for (int j = mileStones[0].length - 2; j >= 0; j--) {
					JPanel ms = mileStones[i][j];
					JPanel mso;
					while (true) {
						if (j >= lim)
							break;
						mso = mileStones[i][j + 1];
						JLabel jl = (JLabel) ms.getComponent(0);
						JLabel jlo = (JLabel) mso.getComponent(0);
						if (jl.getText().isBlank())
							break;

						if (jlo.getText().isBlank()) {
							trocarValores(ms, mso);
							j += 2;
							moveu = true;
						} else if (jlo.getText().equals(jl.getText())) {
							mesclar(ms, mso);
							lim = j;
							moveu = true;
						} else {
							break;
						}
					}
				}
			}
		} else if (direcao == ESQUERDA) {
			for (int i = 0, lim; i < mileStones.length; i++) {
				lim = 0;
				for (int j = 1; j < mileStones[0].length; j++) {
					JPanel ms = mileStones[i][j];
					JPanel mso;
					while (true) {
						if (j <= lim)
							break;
						mso = mileStones[i][j - 1];
						JLabel jl = (JLabel) ms.getComponent(0);
						JLabel jlo = (JLabel) mso.getComponent(0);
						if (jl.getText().isBlank())
							break;

						if (jlo.getText().isBlank()) {
							trocarValores(ms, mso);
							j -= 2;
							moveu = true;
						} else if (jlo.getText().equals(jl.getText())) {
							mesclar(ms, mso);
							lim = j;
							moveu = true;
						} else {
							break;
						}
					}
				}
			}
		} else if (direcao == CIMA) {
			for (int j = 0, lim; j < mileStones[0].length; j++) {
				lim = 0;
				for (int i = 1; i < mileStones.length; i++) {
					JPanel ms = mileStones[i][j];
					JPanel mso;
					while (true) {
						if (i <= lim)
							break;
						mso = mileStones[i - 1][j];
						JLabel jl = (JLabel) ms.getComponent(0);
						JLabel jlo = (JLabel) mso.getComponent(0);
						if (jl.getText().isBlank())
							break;

						if (jlo.getText().isBlank()) {
							trocarValores(ms, mso);
							i -= 2;
							moveu = true;
						} else if (jlo.getText().equals(jl.getText())) {
							mesclar(ms, mso);
							lim = i;
							moveu = true;
						} else {
							break;
						}
					}
				}
			}
		} else if (direcao == BAIXO) {
			for (int j = 0, lim; j < mileStones[0].length; j++) {
				lim = mileStones.length - 1;
				for (int i = mileStones.length - 2; i >= 0; i--) {
					JPanel ms = mileStones[i][j];
					JPanel mso;
					while (true) {
						if (i >= lim)
							break;
						mso = mileStones[i + 1][j];
						JLabel jl = (JLabel) ms.getComponent(0);
						JLabel jlo = (JLabel) mso.getComponent(0);
						if (jl.getText().isBlank())
							break;

						if (jlo.getText().isBlank()) {
							trocarValores(ms, mso);
							i += 2;
							moveu = true;
						} else if (jlo.getText().equals(jl.getText())) {
							mesclar(ms, mso);
							lim = i;
							moveu = true;
						} else {
							break;
						}
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Direcao inválida");
		}

		if (moveu) {
			gerarNovaMileStone();
			if (acabou()) {
				JOptionPane.showMessageDialog(null, "ue, cabo.\nperdeu.\nPotuação: " + getPontuacao(), "GAME OVER",
						JOptionPane.INFORMATION_MESSAGE);
				C.painelCentral.remove(this);
				C.painelCentral.getComponent(0).setVisible(true);
				C.painelCentral.getLayout().layoutContainer(C);
			}
		}
	}

	private void gerarNovaMileStone() {
		List<JPanel> disponiveis = new ArrayList<>();
		for (Component c : this.getComponents()) {
			if (((JLabel) ((JPanel) c).getComponent(0)).getText().isBlank())
				disponiveis.add((JPanel) c);
		}
		if (disponiveis.size() != 0) {
			JPanel jp = disponiveis.get((int) (Math.random() * disponiveis.size()));
			jp.setBackground(cores.get("2"));
			((JLabel) jp.getComponent(0)).setText("2");
		}
	}

	private String getPontuacao() {
		int max = 2;
		for (JPanel[] l : mileStones)
			for (JPanel ms : l)
				if (Integer.valueOf(((JLabel) ms.getComponent(0)).getText()) > max)
					max = Integer.valueOf(((JLabel) ms.getComponent(0)).getText());
		this.C.pack();
		return String.valueOf(max);
	}

	private void mesclar(JPanel jp, JPanel jpo) {
		JLabel jlo = ((JLabel) jpo.getComponent(0));
		JLabel jl = ((JLabel) jp.getComponent(0));
		jlo.setText(String.valueOf(Integer.valueOf(jlo.getText()) * 2));
		jpo.setBackground(cores.get(jlo.getText()));

		jl.setText(" ");
		jp.setBackground(cores.get(jl.getText()));

		if (jlo.getText().equals("2048")) {
			int op = JOptionPane.showConfirmDialog(null, "Você ganhou!\nDeseja continuar?", "Parabens",
					JOptionPane.YES_NO_OPTION);
			if (op == JOptionPane.NO_OPTION) {
				C.painelCentral.remove(this);
				C.telaInicial(this);
			}
		}
	}

	private void trocarValores(JPanel jp, JPanel jpo) {
		JLabel jl = ((JLabel) jp.getComponent(0));
		JLabel jlo = ((JLabel) jpo.getComponent(0));
		jlo.setText(jl.getText());
		jl.setText(" ");

		jp.setBackground(cores.get(jl.getText()));
		jpo.setBackground(cores.get(jlo.getText()));
	}

	public boolean acabou() {
		// pega o tamanho da matriz quadrada
//		int t = mileStones.length;

//		for (int i = 0; i < mileStones.length; i++) {
//			
//		}

		for (int i = 0; i < mileStones.length - 1; i++) {
			for (int j = 0; j < mileStones[0].length - 1; j++) {
				if (mileStones[i][j].getBackground().equals(cores.get(" "))
						|| mileStones[i][j].getBackground().equals(mileStones[i][j + 1].getBackground())
						|| mileStones[i][j].getBackground().equals(mileStones[i + 1][j].getBackground()))
					return false;
			}
			if (mileStones[i][mileStones[0].length - 1].getBackground()
					.equals(mileStones[i + 1][mileStones[0].length - 1].getBackground()))
				return false;
		}
		for (int j = 0; j < mileStones[0].length - 1; j++) {
			if (mileStones[mileStones.length - 1][j].getBackground()
					.equals(mileStones[mileStones.length - 1][j + 1].getBackground())
					|| mileStones[mileStones.length - 1][j + 1].getBackground().equals(cores.get(" "))
					|| mileStones[mileStones.length - 1][j].getBackground().equals(cores.get(" ")))
				return false;
		}
//		C.painelCentral.setVisible(true);

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
