#include "ultimate_ttt.h"
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <time.h>

#define WIN_SCORE 10000000
#define BLOCK_WIN_SCORE 10000
#define EMPTY 0
#define CROSS 1
#define CIRCLE 2
#define DRAW 3

static const int LINE_INDICES[8][3] = {
    {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
    {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Cols
    {0, 4, 8}, {2, 4, 6}             // Diagonals
};

static int get_winner(const int* cells) {
    for (int i = 0; i < 8; i++) {
        int a = cells[LINE_INDICES[i][0]];
        int b = cells[LINE_INDICES[i][1]];
        int c = cells[LINE_INDICES[i][2]];
        if (a != EMPTY && a == b && b == c) return a;
    }
    for (int i = 0; i < 9; i++) if (cells[i] == EMPTY) return EMPTY;
    return DRAW;
}

static int evaluate(int* macroBoard, int player) {
    int opponent = (player == CROSS) ? CIRCLE : CROSS;
    int winner = get_winner(macroBoard);
    if (winner == player) return WIN_SCORE;
    if (winner == opponent) return -WIN_SCORE;

    int score = 0;
    for (int i = 0; i < 9; i++) {
        if (macroBoard[i] == player) score += BLOCK_WIN_SCORE;
        else if (macroBoard[i] == opponent) score -= BLOCK_WIN_SCORE;
    }
    return score;
}

static int minimax(int* board, int* macroBoard, int activeBlock, int depth, int alpha, int beta, int isMax, int botPlayer) {
    int winner = get_winner(macroBoard);
    if (winner != EMPTY || depth == 0) {
        return evaluate(macroBoard, botPlayer);
    }

    int opponent = (botPlayer == CROSS) ? CIRCLE : CROSS;
    int currentPlayer = isMax ? botPlayer : opponent;

    if (isMax) {
        int maxEval = -INT_MAX;
        for (int bIdx = 0; bIdx < 9; bIdx++) {
            if (activeBlock != -1 && bIdx != activeBlock) continue;
            if (macroBoard[bIdx] != EMPTY) continue;

            for (int cIdx = 0; cIdx < 9; cIdx++) {
                if (board[bIdx * 9 + cIdx] == EMPTY) {
                    board[bIdx * 9 + cIdx] = currentPlayer;
                    int oldMacro = macroBoard[bIdx];
                    macroBoard[bIdx] = get_winner(&board[bIdx * 9]);

                    int nextBlock = (macroBoard[cIdx] == EMPTY) ? cIdx : -1;
                    int eval = minimax(board, macroBoard, nextBlock, depth - 1, alpha, beta, 0, botPlayer);

                    board[bIdx * 9 + cIdx] = EMPTY;
                    macroBoard[bIdx] = oldMacro;

                    if (eval > maxEval) maxEval = eval;
                    if (eval > alpha) alpha = eval;
                    if (beta <= alpha) return maxEval;
                }
            }
        }
        return (maxEval == -INT_MAX) ? evaluate(macroBoard, botPlayer) : maxEval;
    } else {
        int minEval = INT_MAX;
        for (int bIdx = 0; bIdx < 9; bIdx++) {
            if (activeBlock != -1 && bIdx != activeBlock) continue;
            if (macroBoard[bIdx] != EMPTY) continue;

            for (int cIdx = 0; cIdx < 9; cIdx++) {
                if (board[bIdx * 9 + cIdx] == EMPTY) {
                    board[bIdx * 9 + cIdx] = currentPlayer;
                    int oldMacro = macroBoard[bIdx];
                    macroBoard[bIdx] = get_winner(&board[bIdx * 9]);

                    int nextBlock = (macroBoard[cIdx] == EMPTY) ? cIdx : -1;
                    int eval = minimax(board, macroBoard, nextBlock, depth - 1, alpha, beta, 1, botPlayer);

                    board[bIdx * 9 + cIdx] = EMPTY;
                    macroBoard[bIdx] = oldMacro;

                    if (eval < minEval) minEval = eval;
                    if (eval < beta) beta = eval;
                    if (beta <= alpha) return minEval;
                }
            }
        }
        return (minEval == INT_MAX) ? evaluate(macroBoard, botPlayer) : minEval;
    }
}

NativeMove get_best_move(int* board, int* macroBoard, int activeBlock, int player, int depth) {
    NativeMove bestMove = {-1, -1, -1, -1};
    int bestValue = -INT_MAX;
    int count = 0;

    static int seeded = 0;
    if (!seeded) {
        srand(time(NULL));
        seeded = 1;
    }

    int boardCopy[81];
    int macroCopy[9];
    memcpy(boardCopy, board, sizeof(int) * 81);
    memcpy(macroCopy, macroBoard, sizeof(int) * 9);

    for (int bIdx = 0; bIdx < 9; bIdx++) {
        if (activeBlock != -1 && bIdx != activeBlock) continue;
        if (macroCopy[bIdx] != EMPTY) continue;

        for (int cIdx = 0; cIdx < 9; cIdx++) {
            if (boardCopy[bIdx * 9 + cIdx] == EMPTY) {
                boardCopy[bIdx * 9 + cIdx] = player;
                int oldMacro = macroCopy[bIdx];
                macroCopy[bIdx] = get_winner(&boardCopy[bIdx * 9]);

                int nextBlock = (macroCopy[cIdx] == EMPTY) ? cIdx : -1;
                int boardVal = minimax(boardCopy, macroCopy, nextBlock, depth - 1, -INT_MAX, INT_MAX, 0, player);

                boardCopy[bIdx * 9 + cIdx] = EMPTY;
                macroCopy[bIdx] = oldMacro;

                if (boardVal > bestValue) {
                    bestValue = boardVal;
                    bestMove.fieldI = bIdx / 3;
                    bestMove.fieldJ = bIdx % 3;
                    bestMove.blockI = cIdx / 3;
                    bestMove.blockJ = cIdx % 3;
                    count = 1;
                } else if (boardVal == bestValue) {
                    count++;
                    if (rand() % count == 0) {
                        bestMove.fieldI = bIdx / 3;
                        bestMove.fieldJ = bIdx % 3;
                        bestMove.blockI = cIdx / 3;
                        bestMove.blockJ = cIdx % 3;
                    }
                }
            }
        }
    }
    return bestMove;
}
