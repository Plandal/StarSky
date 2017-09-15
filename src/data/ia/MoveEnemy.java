/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/ia/MoveLeftPhantom.java 2015-03-11 buixuan.
 * ******************************************************/
package data.ia;

import specifications.EnemyService;
import specifications.PhantomService;
import tools.Position;

public class MoveEnemy implements EnemyService{
    private Position position;

    public MoveEnemy(Position p){ position=p; }

    @Override
    public Position getPosition() { return position; }

    @Override
    public MOVE getAction(int turn) {
        int move = turn%4;
        if(move == 0){
            return MOVE.LEFT;
        }else if(move == 1){
            return MOVE.UP;
        }else if(move == 2){
            return MOVE.DOWN;
        }else {
            return MOVE.LEFT;
        }

    }

    @Override
    public void setPosition(Position p) { position=p; }
}
