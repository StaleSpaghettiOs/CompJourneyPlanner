# JourneyPlanner
Assignment 1 for Comp 261

This is assignment is focussed on implementing an abstract data structure (a graph) to represent bus routes in Brisbane. Bus stops are created as node objects with an ID, name, latitude, and longitude. In this project I have decided to use an Adjacency List, but augmented as an Edge List where the nodes contain two lists, one for outgoing edges and one for incoming, rather than nodes containing a list of neighbouring nodes. Trips are sequences of Stops with the connections (edges) between and the end functionality will allow the user to click or search a stop to highlight the trip(s) which have that stop as a destination.

Required Features:
-Zoom-in/out
-Pan up,down,left,right
-Click a stop to highlight stop and print stop name, id, and all trips going through that stop.
-Search a stop
-Display information on Trips and Stops
-Trie Structure for search function to allow for per-letter search
-Find the closest stop to a selected location and find trips that end there (using quad-tree)



