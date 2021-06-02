package demo.jpa.tree.main;

import org.springframework.stereotype.Service;

import klg.common.tree.enumpath.TreeNodeServiceImpl;


@Service
public class MenuTreeServiceImpl extends TreeNodeServiceImpl<MenuTree,Long> implements MenuTreeService{

}
