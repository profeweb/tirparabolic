package stepFinal;

import processing.core.PApplet;

public class TirParabolic extends PApplet {

    Projectil p;
    Target[] targets;

    float t =0;
    float g = 9.8f;
    float f = 100;
    float h;

    int numShots = 0, numPoints = 0, numTargets = 0;

    boolean disparat = false;

    public static void main(String[] args) {
        PApplet.main("stepFinal.TirParabolic");
    }

    public void settings(){
        size(1600, 800);
    }

    public void setup(){
        h = height/2;
        p = new Projectil(150, h, f);
        setTargets(6, 6);
    }

    public void draw(){
        background(220);

        displayInfo();

        // Dibuixa i actualitza la posició dels targets
        for(int i=0; i<targets.length; i++){
            targets[i].display(this);
            targets[i].update(this);
        }

        // Si no s'ha disparat, configura posició H, força F i direcció del canó
        if (!disparat) {
            float a = map(mouseY, this.h-100, this.h+100, 0, -PI);
            p.setProperties(a, mouseX, mouseY, f, h);
        }

        // Dibuixa el projectil
        p.display(this);

        // Si s'ha disparat i el projectil no ha sortir del camp de joc
        if (disparat && p.x <= width && p.y <= height) {

            // Actualitzam la posició del projectil
            p.update(t, g);
            // Actualitzam el temps
            t += 0.1;

            // Comprovam si el projectil col·lisiona amb els objectius
            for(int i=0; i<targets.length; i++){
                if(targets[i].estat != Target.ESTAT.EXPLOTAT && targets[i].esImpactatPer(this, p)){
                    targets[i].setEstat(Target.ESTAT.EXPLOTAT);
                    numPoints++;
                }
            }
        }
        // Si s'ha disparat i el projectil ha sortir del camp de joc
        else if (disparat && (p.x > width || p.y > height)) {

            // Posam la resta d'objectius a fallats (failed)
            for(int i=0; i<targets.length; i++){
                if(targets[i].estat == Target.ESTAT.PENDENT){
                    targets[i].setEstat(Target.ESTAT.FALLAT);
                }
            }
            // Misatge per resetear la posició dels nous objectius
            textAlign(CENTER); textSize(36); fill(255, 0, 0);
            text("Press R key to set up the next scenario", width/2, height/2);
        }
    }


    void setTargets(int n1, int n2){

        // Crea un número aleatori (entre n1 i n2) d'objectius
        int nt = (int) random(n1, n2);
        targets = new Target[nt];
        numTargets += nt;

        // Posiciona els objectius en el terreny de joc
        for(int i=0; i<nt; i++){
            float x = random(width/2, width);
            float y = random(height/5, 4*height/5);
            float r = random(20, 60);
            targets[i] = new Target(x, y, r);
        }
    }


    void displayInfo(){

        // Títol del joc
        fill(0); textAlign(LEFT); textSize(34);
        text("Tir Parabòlic", 50, 50);

        // Marcador
        fill(0); textAlign(RIGHT);
        text("Score", width - 50, 50);
        textSize(14);
        String percentatge = nf(100*(numPoints/(float)numTargets), 2, 2);
        text("Rate: "+ percentatge+"%", width - 50, 80);
        text("Hits: "+ numPoints + " / " + numTargets, width - 50, 100);
        text("Shots: "+ numShots, width - 50, 120);


        // Instruccions
        fill(0); textSize(14); textAlign(LEFT);
        text("Press S key to shot your cannon.", 50, height-90);
        text("Use MOUSE to set your cannon direction.", 50, height-70);
        text("Press ARROW KEYS to set up your cannon.", 50, height-50);
        text("UP: move up, DOWN: move down, LEFT: decrease force, RIGHT: increase force.", 50, height-30);
    }

    public void keyPressed() {

        // Dispara el canó
        if (key == 's' || key=='S') {
            if(!disparat){
                numShots++;
            }
            disparat = true;
        }
        // Reseteja la posició dels objectius
        else if (key == 'r') {
            disparat = false;
            t = 0;
            p.update(t, g);
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
