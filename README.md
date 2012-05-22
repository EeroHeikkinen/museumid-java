# museumid-java

 museumid-java is a Java implementation of the MuseumID Museum Object Identifier specification. For more information about MuseumID, visit [the project's website](http://museumid.net/).

## Usage
```
// Create Museum Namespace from the base domain name
MNS	mns = new MNS("nba.fi");

// Create Museum Object Identifier using the object's inventory number and the Museum Namespace
MOI	moi = new MOI("TEST1", mns);

// Results in: "urn:moi:ee167043-81b5-5228-a375-b95fcc1fcdd2"
String urn = moi.getURN();
```

## License

 [GPLv3](http://www.gnu.org/licenses/gpl.txt)