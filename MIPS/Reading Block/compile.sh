echo "Compiling $1 ..."
echo -n "	"
expect assemble.exp $1 ${1%.s}.bin | sed '1,7d' | sed '2d'
