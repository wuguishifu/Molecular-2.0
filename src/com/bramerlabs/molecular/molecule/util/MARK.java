package com.bramerlabs.molecular.molecule.util;

/*
adapted from the MARK algorithm, https://www.sciencedirect.com/science/article/pii/089855299090106I

steps:
a)  input structural params of compound;
b)  if there are no pre-side chains, go to step (f) and then (h);
c)  take into consideration every pre-side chain and, if any pre-ramifications are found, process them in order to
    obtain a sequence of carbon atoms as long as possible;
d)  compare the side chains (from the pre-side chains as per (c)) with the ground chain and:
        1) determine the longest chain;
        2) rearrange the compound;
e)  define the name of the side chains and ramifications using the corresponding prefixes;
f)  define the name of the longest chain;
g)  if side chains are present, consider the possible numbering directions and select those determined by rule A-2.2;
h)  output the complete name (string) of the molecule

data is stored in a bi-dimensional matrix M(i, j), where
    i = (maximum number of pre-side chains * 2) + 4;
    j = dimension of the longest pre-side chain + 1;

*/

import com.bramerlabs.molecular.molecule.Molecule;
import org.openscience.cdk.iupac.parser.MoleculeBuilder;

public class MARK {



}
