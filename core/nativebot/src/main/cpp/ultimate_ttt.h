#ifndef ULTIMATE_TTT_H
#define ULTIMATE_TTT_H

#ifdef __cplusplus
extern "C" {
#endif

typedef struct {
    int fieldI;
    int fieldJ;
    int blockI;
    int blockJ;
} NativeMove;

// board: 9x9 array where 0=Empty, 1=Cross, 2=Circle
// macroBoard: 9 array for block winners: 0=InProgress, 1=Cross, 2=Circle, 3=Draw
// activeBlock: -1 if any block, otherwise index 0-8 (row * 3 + col)
NativeMove get_best_move(int* board, int* macroBoard, int activeBlock, int player, int depth, int timeout_ms);

#ifdef __cplusplus
}
#endif

#endif
