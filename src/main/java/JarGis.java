import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;

import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

import com.itextpdf.text.*;
import com.itextpdf.text.List;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.filechooser.FileSystemView;

public class JarGis{

    public static void mostrarInterface() {
        Looca looca = new Looca();
        Processador processador = looca.getProcessador();
        Memoria memoria = looca.getMemoria();

        // fonte
        Font verdanaFont = new Font("Verdana", Font.PLAIN, 12);

        UIManager.put("Label.font", new FontUIResource(verdanaFont));
        UIManager.put("Button.font", new FontUIResource(verdanaFont));

        // cor da fonte em todos os textos
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("Button.foreground", Color.BLACK);

        // Criando uma janela Swing
        JFrame frame = new JFrame("Informações do Hardware");

        frame.setResizable(false);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criando um paineis
        JPanel panelMaior = new JPanel();
        panelMaior.setBackground(Color.decode("#080B16"));
        JPanel panelMenor = new JPanel();
        panelMenor.setBackground(Color.decode("#daebed"));
        panelMenor.setPreferredSize(new Dimension(500, 450));

        int topMargin = 20;
        int leftMargin = 10;
        int bottomMargin = 10;
        int rightMargin = 10;
        panelMenor.setBorder(new EmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

        panelMaior.add(panelMenor);

        //botão de download
        JButton button = new JButton("Download de Relatório");
        button.setBackground(new Color(0, 183, 234));
        button.setForeground(Color.BLACK);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(16, 122, 135));
                button.setForeground(Color.WHITE);// Muda a cor de fundo quando o mouse entra no botão
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 183, 234));
                button.setForeground(Color.BLACK);
            }
        });

        // infos cpu
        JLabel cpuLabel = new JLabel("<html><center><b><u><font color='#FF0000'> Sobre a CPU:</font></u></b></center><br>" +
                "Nome: " + processador.getNome() + "<br>" +
                "Fabricante: " + processador.getFabricante() + "<br>" +
                "Frequência: " + processador.getFrequencia() + " GHz</html>");
        cpuLabel.setFont(cpuLabel.getFont().deriveFont(Font.PLAIN));

        // infos ram
        JLabel ramLabel = new JLabel("<html><br><br><center><b><u><font color='#FF0000'>Sobre a memória RAM:</font></u></b></center>" +
                "<br>Quantidade total: " + memoria.getTotal() + " GB" +
                "<br>Quantidade usada: " + memoria.getEmUso() + " GB</html>");
        ramLabel.setFont(ramLabel.getFont().deriveFont(Font.PLAIN));

        // infos disco
        com.github.britooo.looca.api.group.discos.DiscoGrupo discoInfo = looca.getGrupoDeDiscos();
        JTextPane discoTextPane = new JTextPane();
        discoTextPane.setEditable(false);
        discoTextPane.setBackground(null); // Para tornar o fundo transparente
        discoTextPane.setContentType("text/html"); // Define o conteúdo como HTML

        String discoText = "<html><br><center><b><u><font color='#FF0000'>Sobre o DISCO:</font></u></b></center><br>" +
                discoInfo.getDiscos() + "</html>";
        discoTextPane.setText(discoText);

        discoText = "<font face='Verdana' size='12'>" + discoText + "</font>";

        discoTextPane.setPreferredSize(new Dimension(380, 250));

        // Adicionando os componentes ao painel
        panelMenor.add(button);
        panelMenor.add(cpuLabel);
        panelMenor.add(ramLabel);
        panelMenor.add(discoTextPane);


        // Adicionando o painel à janela
        frame.add(panelMaior);

        // Centralizando a janela na tela
        frame.setLocationRelativeTo(null);

        // Exibindo a janela
        frame.setVisible(true);

        // Criando um documento PDF
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria um documento PDF
                Document documento = new Document();

                try {
                    // pegando o diretório downloads do usuário
                    String diretorioUsuario = System.getProperty("user.home");

                    // Define o diretório de downloads
                    String diretorioDownloads = diretorioUsuario + "/Downloads/";

                    // nome do arquivo pdf
                    String nomeArquivo = diretorioDownloads + "relatorioNotebook.pdf";


                    // Cria um objeto PdfWriter para escrever o documento no arquivo
                    PdfWriter.getInstance(documento, new FileOutputStream(nomeArquivo));

                    // Abre o documento para escrita
                    documento.open();

                    // titulo do relatorio
                    Paragraph titulo = new Paragraph("Relatório de Informações de Hardware", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    documento.add(titulo);
                    documento.add(new Paragraph("\n"));

                    // lista - topicos
                    List listaCpu = new List(List.UNORDERED);
                    listaCpu.setListSymbol("\u2022"); // bolinha preta

                    List listaRam = new List(List.UNORDERED);
                    listaRam.setListSymbol("\u2022"); // bolinha preta

                    List listaDisco= new List(List.UNORDERED);
                    listaDisco.setListSymbol("\u2022"); // bolinha preta

                    // Adiciona os dados da CPU ao documento
                    listaCpu.add(new ListItem(" Informações da CPU:"));
                    documento.add(listaCpu);
                    documento.add(new Paragraph("Nome: " + processador.getNome()));
                    documento.add(new Paragraph("Fabricante: " + processador.getFabricante()));
                    documento.add(new Paragraph("Frequência: " + processador.getFrequencia() + " GHz"));
                    documento.add(new Paragraph("\n"));

                    // Adiciona os dados da memória RAM ao documento
                    listaRam.add(new ListItem(" Informações da memória RAM:"));
                    documento.add(listaRam);
                    documento.add(new Paragraph("Quantidade total: " + memoria.getTotal() + " GB"));
                    documento.add(new Paragraph("Quantidade usada: " + memoria.getEmUso() + " GB"));
                    documento.add(new Paragraph("\n"));

                    // Adiciona os dados do disco ao documento
                    listaDisco.add(new ListItem(" Informações do disco:"));
                    documento.add(listaDisco);
                    documento.add(new Paragraph(String.valueOf(discoInfo.getDiscos())));

                    // Fecha o documento
                    documento.close();

                    // mensagem que o relatório foi gerado com sucesso
                    JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso: " + nomeArquivo);

                } catch (DocumentException | FileNotFoundException ex) {
                    // mensagem de erro ao gerar o relatório
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + ex.getMessage());
                }
            }
        });
    }
}
