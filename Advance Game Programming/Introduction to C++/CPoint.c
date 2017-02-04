#include <stdio.h>
#include <stdlib.h>

/*  data
    int x, y;
*/
typedef struct Point {
	int x,y;
}Point;

/*constructor
    Point(){
        x = y = 0;
    }
*/
void Construct_Point(Point* point, int size){
    //Set all values to 0
	for (int i = 0; i < size; ++i){
		point[i].x = 0;
		point[i].y = 0;
	}//end if 
}//end Construct_Point

/*  print to standard output
    void print() const
    {
        std::cout << "[" << x << "," << y << "]" << std::endl;
    }
*/
void print(Point* point){
	printf("[%d,%d]\n", point->x, point->y);
}//end print


/*
  add point componentwise
  void add(const Point &p)
  {
    x += p.x;
    y += p.y;
  }
*/
void add(Point* pointA, Point* pointB){
	pointA->x = pointA->x + pointB->x;
	pointA->y = pointA->y + pointB->y;
}//end add

int main(){

/*
    const int N = 200;
    Point *A = new Point[N], sum;
*/
	const int N = 200;
	Point* sum = malloc(sizeof(Point));
	Point* A = malloc(N * sizeof(Point)); 
	Construct_Point(A, N);
	Construct_Point(sum, 1);

/*
    for (int i=0; i < N; ++i) {
        A[i].x = i; A[i].y = -i;
        sum.add(A[i]);
    }
*/
	for (int i = 0; i < N; ++i){
		A[i].x = i;
		A[i].y = -i;
		add(sum, &A[i]);
	}//end forloop

    //sum.print();
	print(sum);

    //delete [] A;
	free(A);
    free(sum);

}//end main
