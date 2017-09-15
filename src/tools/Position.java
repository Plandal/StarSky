/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: tools/Position.java 2015-03-11 buixuan.
 * ******************************************************/
package tools;

public class Position {
    public double x,y;
    public Position(double x, double y){

        this.x=x;
        this.y=y;
    }

    public Position add(Position position) {
        return new Position(position.x + this.x, position.y + this.y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
