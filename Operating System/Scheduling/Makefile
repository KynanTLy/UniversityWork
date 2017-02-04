CC := gcc
CFLAGS := -Wall -Wextra -Wconversion -std=c99 -D_SVID_SOURCE -g
LDFLAGS := -lm -lpthread
OBJECTS := factory.o assemblers.o packers.o

all: Factory

factory.o: factory.c factory.h
	$(CC) $(CFLAGS) -c factory.c $(LDFLAGS)

assembler.o: assemblers.c assemblers.h
	$(CC) $(CFLAGS) -c assemblers.c $(LDFLAGS)

packers.o: packers.c packers.h
	$(CC) $(CFLAGS) -c packers.c $(LDFLAGS)

clean:
	rm *.o Factory

Factory: factory.o assemblers.o packers.o
	$(CC) $(CFLAGS) factory.o assemblers.o packers.o -o Factory $(LDFLAGS)
