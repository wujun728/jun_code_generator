package demo.jpa.tree.main;

import klg.common.dataaccess.BaseRepository;
import klg.common.tree.enumpath.TreeNodeRepository;

public interface MenuTreeDAO extends TreeNodeRepository<MenuTree, Long>, BaseRepository<MenuTree, Long> {

}
