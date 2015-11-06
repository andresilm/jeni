GRAMMAR="valuation.xml"
BEAM_SIZE=5

#LEXICON="lexicon-06112015.lex"
LEXICON="lexicon-06112015-after.lex"

#TESTSUITE="testsuite-06112015.geni"
TESTSUITE="testsuite-06112015-after.geni"

#OUTPUT="output-06112015.txt"
OUTPUT="output-06112015-after.txt"

java -Xmx3g -jar Pjeni.jar $GRAMMAR $LEXICON $TESTSUITE $BEAM_SIZE $OUTPUT
