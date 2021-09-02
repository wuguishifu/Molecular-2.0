package com.bramerlabs.molecular.main;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.iupac.parser.AttachedGroup;
import org.openscience.cdk.iupac.parser.ParseException;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.elements.IRenderingElement;
import org.openscience.cdk.renderer.font.IFontManager;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.IGeneratorParameter;
import org.openscience.cdk.renderer.visitor.IDrawVisitor;

import java.awt.geom.AffineTransform;
import java.util.*;

public class MoleculeBuilder extends org.openscience.cdk.iupac.parser.MoleculeBuilder {

    public IAtomContainer buildMeAMolecule() {

        List<AttachedGroup> groups = new ArrayList<>();
        List<AttachedGroup> substituents = new ArrayList<>();

        try {
            return buildMolecule(7, substituents, groups, false, "hi");
        } catch (ParseException | CDKException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void main(String[] args) {
        MoleculeBuilder b = new MoleculeBuilder();
        IAtomContainer a = b.buildMeAMolecule();

        IGenerator<IAtomContainer> ab = new IGenerator<IAtomContainer>() {
            @Override
            public List<IGeneratorParameter<?>> getParameters() {
                return null;
            }

            @Override
            public IRenderingElement generate(IAtomContainer iAtomContainer, RendererModel rendererModel) {
                return null;
            }
        };

        ArrayList<IGenerator<IAtomContainer>> c = new ArrayList<>();
        c.add(ab);

        AtomContainerRenderer renderer = new AtomContainerRenderer(c, new IFontManager() {
            @Override
            public void setFontForZoom(double v) {

            }

            @Override
            public void setFontStyle(FontStyle fontStyle) {

            }

            @Override
            public void setFontName(String s) {

            }
        });

        IDrawVisitor dv = new IDrawVisitor() {
            @Override
            public void setFontManager(IFontManager iFontManager) {

            }

            @Override
            public void setRendererModel(RendererModel rendererModel) {

            }

            @Override
            public void visit(IRenderingElement iRenderingElement) {

            }

            @Override
            public void setTransform(AffineTransform affineTransform) {

            }
        };

        renderer.paint(a, dv);

    }

}
