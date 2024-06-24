package step06;

import processing.core.PApplet;

public class TirParabolic extends PApplet {

    // Array d'objectes de classe Target
    Target[] targets;

    // Declaram 1 variable de classe Projectil
    Projectil p;

    // Força i altura del canó
    float f = 100;
    float h = 400;

    // Dispar del projectil fet o no
    boolean disparat = false;

    // Temps
    float t =0;

    // Gravetat
    float g = 9.8f;

    public static void main(String[] args) {
        PApplet.main("step06.TirParabolic");
    }

    public void settings(){
        size(1600, 800);
    }

    public void setup(){
        // Crea el projectil amb el constructor
        p = new Projectil(100, h, 50);

        // Crea els targets
        setTargets(3, 9);

    }

    public void draw(){
        background(220);

        // Dibuixa els targets
        for(int i=0; i<targets.length; i++) {
            targets[i].display(this);
        }

        // Si no s'ha disparat, configura posició H, força F i direcció A del canó
        if (!disparat) {
            float a = map(mouseY, this.h-100, this.h+100, 0, -PI);
            p.setProperties(a, f, h);
        }
        else {
            // Actualitzam la posició del projectil
            p.update(t, g);
            // Actualitzam el temps
            t += 0.1;

            // Comprovam si el projectil col·lisiona amb els objectius
            for(int i=0; i<targets.length; i++){
                if(targets[i].estat != Target.ESTAT.EXPLOTAT && targets[i].esImpactatPer(this, p)){
                    targets[i].setEstat(Target.ESTAT.EXPLOTAT);
                }
            }
        }

        // Dibuixa el projectil
        p.display(this);
    }

    void setTargets(int n1, int n2){

        // Crea un número aleatori (entre n1 i n2) d'objectius
        int nt = (int) random(n1, n2);
        targets = new Target[nt];

        // Posiciona els objectius en el terreny de joc
        for(int i=0; i<nt; i++){
            float x = random(width/2, width);
            float y = random(height/5, 4*height/5);
            float r = random(20, 60);
            targets[i] = new Target(x, y, r);
        }
    }

    public void keyPressed() {

        // Dispara el canó
        if (key == 's' || key=='S') {
            disparat = true;
        }
        // Reseteja la posició dels objectius
        else if (key == 'r') {
            disparat = false;
            t = 0;
            setTargets(3, 12);
        }
        // Augmenta la força del dispar
        else if (keyCode == RIGHT) {
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
