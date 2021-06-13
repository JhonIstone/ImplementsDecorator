package decorators;

import interfaces.PizzaComponent;
import interfaces.PizzaDecorator;

public class AzeitonaDecorator extends PizzaDecorator{

    public AzeitonaDecorator(PizzaComponent decorated){
        this.decorated = decorated;
    }

    @Override
    public void preparar() {
        // TODO Auto-generated method stub
        decorated.preparar();
        System.out.println("Adicionando Azeitona");
    }
    
}
