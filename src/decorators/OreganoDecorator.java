package decorators;

import interfaces.PizzaComponent;
import interfaces.PizzaDecorator;

public class OreganoDecorator extends PizzaDecorator {

    public OreganoDecorator (PizzaComponent decorated){
        this.decorated = decorated;
    }
    @Override
    public void preparar() {
        // TODO Auto-generated method stub
        decorated.preparar();
        System.out.println("Adicionando Oregano");
    }
    
}
