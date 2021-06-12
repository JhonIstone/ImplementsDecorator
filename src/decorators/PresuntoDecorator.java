package decorators;

import interfaces.PizzaComponent;
import interfaces.PizzaDecorator;

public class PresuntoDecorator extends PizzaDecorator{

    public PresuntoDecorator(PizzaComponent decorated) {
        this.decorated = decorated;
    }
    
    @Override
    public void preparar() {
        // TODO Auto-generated method stub
        decorated.preparar();
        System.out.println("Colocando presunto");
    }
    
}
