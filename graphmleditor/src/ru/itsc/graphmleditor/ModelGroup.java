/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.itsc.graphmleditor;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


public final class ModelGroup extends Group {
    public final Rotate rx = new Rotate(0, Rotate.X_AXIS);
    public final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    public final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
    public final Translate translate = new Translate();
    
    public ModelGroup() {
        super(); 
        getTransforms().addAll(translate, rz, ry, rx); 
    }
    
    public ModelGroup(Node node) {
        super(); 
        getTransforms().addAll(translate, rz, ry, rx); 
        getChildren().add(node);
    }
}
