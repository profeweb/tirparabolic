package step04;

import processing.core.PApplet;

public class TirParabolic extends PApplet {

    // Declara 3 variabes de classe Target
    Target t1, t2, t3;

    // Declaram 1 variable de classe Projectil
    Projectil p;

    // Força i altura del canó
    float f = 100;
    float h = 400;

    public static void main(String[] args) {
        PApplet.main("step04.TirParabolic");
    }

    public void settings(){
        size(1600, 800);
    }

    public void setup(){

        // Crea els targets amb el constructor
        t1 =  new Target(300, 100, 20);
        t2 =  new Target(600, 200, 50);
        t3 =  new Target(500, 500, 60);

        // Modifica l'estat dels targets 1 i 2
        t1.setEstat(Target.ESTAT.EXPLOTAT);
        t2.setEstat(Target.ESTAT.FALLAT);

        // Crea el projectil amb el constructor
        p = new Projectil(100, h, 50);

    }

    public void draw(){
        background(220);

        // Actualitza el canó
        float a = map(mouseY, this.h-100, this.h+100, 0, -PI);
        p.setProperties(a, f, h);

        // Dibuixa els targets
        t1.display(this);
        t2.display(this);
        t3.display(this);

        // Dibuixa el projectil
        p.display(this);
    }

    public void keyPressed() {

        // Augmenta la força del dispar
        if (keyCode == RIGHT) {
            f += 10;
            f = constrain(f, 10, 300);
        }
        // Redueix la força del dispar
        else if (keyCode == LEFT) {
            f -= 10;
            f = constrain(f, 10, 300);
        }
        // Augmenta l'altura H del canó
        else if (keyCode == UP) {
            h -= 10;
        }
        // Redueix l'altura H del canó
        else if (keyCode == DOWN) {
            h += 10;
        }
    }

}
