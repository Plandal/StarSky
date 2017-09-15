/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/ia/MoveLeftPhantom.java 2015-03-11 buixuan.
 * ******************************************************/
package data.ia;

import specifications.ShootService;
import tools.Position;

public class ShootRight implements ShootService{
    private Position position;

    public ShootRight(Position p){ position=p; }

    @Override
    public Position getPosition() { return position; }

    @Override
    public ShootService.MOVE getAction() { return ShootService.MOVE.RIGHT; }

    @Override
    public void setPosition(Position p) { position=p; }
}
