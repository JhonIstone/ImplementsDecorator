package main;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import interfaces.PizzaComponent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MainScream extends JFrame implements ActionListener{
    private DefaultListModel<String> sabores = new DefaultListModel<String>();
    private JList<String> listaDecorators = new JList<String>(sabores);
    private DefaultListModel<String> selecionados = new DefaultListModel<String>();
    private JList<String> listaSelected = new JList<String>(selecionados);
    private JButton up = new JButton("up");
    private JButton down = new JButton("down");
    private JButton adicionar = new JButton("add");
    private JButton remover = new JButton("remove");
    private JPanel panelListDecorators = new JPanel(new GridLayout(0,1));
    private JPanel panelButons = new JPanel(new GridLayout(0,1));
    private JPanel panelListSelected = new JPanel(new GridLayout(0,1));
    private JPanel panelUpDown = new JPanel(new GridLayout(0,1));
    private JButton make = new JButton("make");
    private JButton refresh = new JButton("Recarregar Sabores");
    private JPanel panelMakeRefresh = new JPanel(new GridLayout(0,1));
    private JPanel panelPrincipal = new JPanel(new FlowLayout());
    
    public MainScream() throws MalformedURLException{
        this.panelListDecorators.setBorder(BorderFactory.createTitledBorder("Sabores Disponiveis"));
        this.panelListSelected.setBorder(BorderFactory.createTitledBorder("Sabores Escolhidos"));
        File currentDir = new File("./src/plugins");
        String[] pluginsName = currentDir.list();
        URL[] jars = new URL[pluginsName.length];
        for(int i = 0; i < pluginsName.length; i++) {
            jars[i] = new File("./src/plugins" + pluginsName[i]).toURL();
        }
        URLClassLoader ucl = new URLClassLoader(jars);

        for (int i = 0; i < pluginsName.length; i++) {
            this.sabores.addElement(pluginsName[i].split("Decorator")[0]);
        }

        this.setLayout(new BorderLayout());
        listaDecorators.setPreferredSize(new Dimension(200, 300));
        listaSelected.setPreferredSize(new Dimension(200, 300));

        panelListDecorators.add(listaDecorators);
        
        panelButons.add(adicionar);
        panelButons.add(remover);
        
        panelListSelected.add(listaSelected);

        adicionar.addActionListener(this);
        remover.addActionListener(this);

        up.addActionListener(this);
        down.addActionListener(this);
        
        panelUpDown.add(up);
        panelUpDown.add(down);

        make.addActionListener(this);
        refresh.addActionListener(this);

        panelMakeRefresh.add(make);
        panelMakeRefresh.add(refresh);

        panelPrincipal.add(panelListDecorators);
        panelPrincipal.add(panelButons);
        panelPrincipal.add(panelListSelected);
        panelPrincipal.add(panelUpDown);
        panelPrincipal.add(panelMakeRefresh);

        this.add(panelPrincipal);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setTitle("Pizzaria Ifba");
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getActionCommand().equalsIgnoreCase("add")){
            if(listaDecorators.getSelectedValue() != null){
                selecionados.addElement(listaDecorators.getSelectedValue());
            }
        }else if (e.getActionCommand().equalsIgnoreCase("remove")){
            if(listaSelected.getSelectedValue() != null){
                selecionados.removeElement(listaSelected.getSelectedValue());
                listaSelected.setSelectedIndex(listaSelected.getSelectedIndex()+1);
            }
        }else if (e.getActionCommand().equalsIgnoreCase("up")){
            if(listaSelected.getSelectedValue() != null){
                if(listaSelected.getSelectedIndex() != 0){
                    String valor = listaSelected.getSelectedValue();
                    int index = listaSelected.getSelectedIndex()-1;
                    selecionados.insertElementAt(valor, index);
                    selecionados.removeElementAt(listaSelected.getSelectedIndex());
                    listaSelected.setSelectedIndex(index);     
                }
            }
        }else if (e.getActionCommand().equalsIgnoreCase("down")){
            if(listaSelected.getSelectedValue() != null){
                if(listaSelected.getSelectedIndex() != selecionados.size()-1){
                    System.out.println(listaSelected.getSelectedIndex());
                    String valor = listaSelected.getSelectedValue();
                    int index = listaSelected.getSelectedIndex()+1;
                    selecionados.removeElementAt(listaSelected.getSelectedIndex());
                    selecionados.add(index,valor);
                    listaSelected.setSelectedIndex(index);
                }
            }
        }else if (e.getActionCommand().equalsIgnoreCase("make")){
            String[] toMake = new String[selecionados.getSize()];
            for (int i = 0; i < toMake.length; i++) {
                toMake[i] = selecionados.get(i).concat("Decorator");
            }
            File currentDir = new File("./src/plugins");
            String[] pluginsName = currentDir.list();
            URL[] jars = new URL[pluginsName.length];
            for(int i = 0; i < pluginsName.length; i++) {
                try {
                    jars[i] = new File("./src/plugins" + pluginsName[i]).toURL();
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            URLClassLoader ucl = new URLClassLoader(jars);
            try {
                PizzaComponent[] pizzas = new PizzaComponent[toMake.length];
                pizzas[toMake.length-1] = (PizzaComponent) Class.forName("decorators" + "." + toMake[toMake.length-1], true, ucl).getDeclaredConstructor(PizzaComponent.class).newInstance(new PizzaBasica());
                for(int i = toMake.length-2; i >= 0 ; i--) {
                    pizzas[i] = (PizzaComponent) Class.forName("decorators" + "." + toMake[i], true, ucl).getDeclaredConstructor(PizzaComponent.class).newInstance(pizzas[i+1]);
                }
                pizzas[0].preparar();
                selecionados.removeAllElements();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException
                    | ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
        }else if (e.getActionCommand().equalsIgnoreCase("Recarregar Sabores")){
            sabores.removeAllElements();
            File currentDir = new File("./src/plugins");
            String[] pluginsName = currentDir.list();
            URL[] jars = new URL[pluginsName.length];
            for(int i = 0; i < pluginsName.length; i++) {
                try {
                    jars[i] = new File("./src/plugins" + pluginsName[i]).toURL();
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            for (int i = 0; i < pluginsName.length; i++) {
                sabores.addElement(pluginsName[i].split("Decorator")[0]);
            }
        }
    }
}
