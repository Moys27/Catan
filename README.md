# Catan 2.0
Ce projet est une reprise moins élaborée que l'original des "COLONS DE CATAN" purement codé en java,
plus précisément avec la version 17.1 de Java.

Le jeu se joue à plusieurs (3 ou 4 joueurs) :
* soit avec des adversaires virtuelles (ie par le bia d'IA, dont les décisions sont purement aléatoire pour l'instant)
* soit avec des adversaires humains dont les données sont configurées au début du jeu.


#Déroulement
L'adaptation reprend les règles générales du jeu, c'est-à-dire que :
* Le joueur est d'abord invité à placer ses premières colonies au début du jeu
* Ensuite, il aura la possibilité d'étendre ses colonies selon les ressources qu'il a a sa disposition
* Le joueur ayant atteint 10 points de victoire gagne la partie

#Lancement du jeu
Pour pouvoir lancer le jeu, il faut exécuter les lignes de commandes suivantes :

``$ cd src/ ``

``$ javac -Xlint:unchecked Catan/Board/*.java Catan/Card/*.java Catan/gui/controller/*.java Catan/gui/model/*.java Catan/gui/view/object/*.java Catan/gui/view/run/*.java Catan/Players/*.java Catan/Run/*.java
``

``$ java Catan.Run.InterfaceConsole`` 
pour l'interface console, ou bien :
``$ java Catan.gui.view.run.GUI `` pour l'interface graphique.


**Projet conçu, implémenté et déployé par Ana Parres Cerezo et Tania Mahandry.**




