//INIT VARIABLES 
std::vector<std::vector<Tile>> grid;
//GenMap used for closed set
mutable std::vector<std::vector<int>> genMap;
mutable int generatorCounter;
int width;
int height;

//CACHE ENTRY WHICH INCLUDE THE A MATRIX OF VALUE LOCATIONS AND SIZE OF OBJECT
struct cache{
	public:
	cache(int size, std::vector<std::vector<bool>> entry) : objectsize(size), cachedTile(entry) {}
	int objectsize;
	std::vector<std::vector<bool>> cachedTile;
};

//THE CACHE
mutable std::vector<cache> inCache;

//INSERT CACHE ENTRY INTO LIST OF CACHE
void insertCache(int size, std::vector<std::vector<bool>> newRegion) const;

//CALCULATE HEURISTIC COST
double heuristic_cost(int sx, int sy, int tx, int ty) const;

//RETURN CACHE ENTRY AT INDEX
int indexCache(int size, int sx, int sy) const;

//RETURN THE RESULT MATRIX TABLE FROM CACHE ENTRY
bool getEntryCache(int index, int ts, int ty) const;

//RETURN IF I CAN GET TO TARGET X,Y IN ONE STEP
bool canGetNext(int sx, int sy, int tx, int ty, int objectsize) const;

//CHECK IF THE VALUE IS WITHIN BOUND
bool checkBound(int x, int y) const;

//RETURN THE DIRECTION FROM POINT A -> POINT B
Grid::Direction getDisDir(std::pair<int, int> current, std::pair<int, int> target) const;

//RETURN THE COST FOR THE INPUTED DIRECTION
int returnCost(Grid::Direction d)const;

//RETURNS THE COST OF THE PATH AS WELL AS PUSH TO PATH THE PATH COMPUTED BY A STAR
int Reconstruct_path(std::map<std::pair<int, int>, std::pair<std::pair<int,int>, Direction>> cameFrom,  std::vector<Direction> &path, std::pair<int,int> current, double cost) const;


