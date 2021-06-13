package main;

import interfaces.PizzaComponent;

public class PizzaBasica implements PizzaComponent{

    @Override
    public void preparar() {
        // TODO Auto-generated method stub
        System.out.println("Preparando a massa + molho + queijo");
    }
    
}
