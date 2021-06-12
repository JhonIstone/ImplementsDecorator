package main;

import interfaces.PizzaComponent;

public class PizzaSimples implements PizzaComponent{

    @Override
    public void preparar() {
        // TODO Auto-generated method stub
        System.out.println("Preparando massa + molho + queijo");
    }
    
}
