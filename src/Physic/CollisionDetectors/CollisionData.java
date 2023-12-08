package Physic.CollisionDetectors;

import Physic.Vec2d;
/**
 * Klasa reprezentująca dane o kolizji.
 *
 * <p>Obiekt tej klasy przechowuje informacje o tym, czy wystąpiła kolizja, oraz dodatkowe dane związane
 * z kolizją, takie jak współrzędne punktu kolizji (p), wektor przemieszczenia (delta), oraz wartości
 * c1, c2, c3, l.</p>
 *
 */
public class CollisionData {

    /**
     * Informacja o tym, czy kolizja wystąpiła.
     */
    public boolean colisionOccured;

    /**
     * Wartość c1 używana w analizie kolizji.
     */
    public double c1;

    /**
     * Wartość c2 używana w analizie kolizji.
     */
    public double c2;

    /**
     * Wartość c3 używana w analizie kolizji.
     */
    public double c3;

    /**
     * Wartość l używana w analizie kolizji.
     */
    public double l;

    /**
     * Wektor przemieszczenia.
     */
    public Vec2d delta;

    /**
     * Współrzędne punktu kolizji.
     */
    public Vec2d p;

    /**
     * Zwraca reprezentację tekstową obiektu CollisionData.
     *
     * @return Tekstowa reprezentacja obiektu.
     */
    @Override
    public String toString() {
        return String.format("(colisionOccured: %b, p: %s, delta: %s, c1: %f, c2: %f, c3: %f, l: %f)",
                colisionOccured, p, delta, c1, c2, c3, l);
    }
}
