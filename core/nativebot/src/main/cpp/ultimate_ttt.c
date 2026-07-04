#include "ultimate_ttt.h"
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <time.h>

#define WIN_SCORE 10000000
#define BLOCK_WIN_SCORE 50000
#define POTENTIAL_LINE_SCORE 500
#define CENTER_BONUS 1000
#define CORNER_BONUS 300

#define EMPTY 0
#define CROSS 1
#define CIRCLE 2
#define DRAW 3

static const int LINE_INDICES[8][3] = {
    {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
    {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Cols
    {0, 4, 8}, {2, 4, 6}             // Diagonals
};

static const int MOVE_PRIORITY[9] = {4, 0, 2, 6, 8, 1, 3, 5, 7}; // Center, Corners, Sides

static int timeout_occurred = 0;
static clock_t end_time;

static int get_winner(const int* cells) {
    for (int i = 0; i < 8; i++) {
        int a = cells[LINE_INDICES[i][0]];
        int b = cells[LINE_INDICES[i][1]];
        int c = cells[LINE_INDICES[i][2]];
        if (a != EMPTY && a != DRAW && a == b && b == c) return a;
    }
    for (int i = 0; i < 9; i++) if (cells[i] == EMPTY) return EMPTY;
    return DRAW;
}

static int count_potential(const int* cells, int player) {
    int count = 0;
    int opponent = (player == CROSS) ? CIRCLE : CROSS;
    for (int i = 0; i < 8; i++) {
        int p = 0, e = 0;
        for (int j = 0; j < 3; j++) {
            int val = cells[LINE_INDICES[i][j]];
            if (val == player) p++;
            else if (val == EMPTY) e++;
            else if (val == opponent) { p = 0; break; }
        }
        if (p == 2 && e == 1) count++;
    }
    return count;
}

static int evaluate(int* board, int* macroBoard, int activeBlock, int isBotTurn, int botPlayer) {
    int opponent = (botPlayer == CROSS) ? CIRCLE : CROSS;
    int winner = get_winner(macroBoard);
    if (winner == botPlayer) return WIN_SCORE;
    if (winner == opponent) return -WIN_SCORE;

    int score = 0;

    // Penalty for giving free move, or bonus for having one
    if (activeBlock == -1) {
        if (isBotTurn) score += 5000;
        else score -= 8000;
    }

    // Macro board evaluation
    for (int i = 0; i < 9; i++) {
        int val = macroBoard[i];
        int multiplier = (i == 4) ? 3 : ((i == 0 || i == 2 || i == 6 || i == 8) ? 2 : 1);

        if (val == botPlayer) score += BLOCK_WIN_SCORE * multiplier;
        else if (val == opponent) score -= BLOCK_WIN_SCORE * multiplier;
        else if (val == EMPTY) {
            score += count_potential(macroBoard, botPlayer) * POTENTIAL_LINE_SCORE;
            score -= count_potential(macroBoard, opponent) * POTENTIAL_LINE_SCORE;
        }
    }

    // Micro board evaluation for non-finished blocks
    for (int i = 0; i < 9; i++) {
        if (macroBoard[i] == EMPTY) {
            int* micro = &board[i * 9];
            score += count_potential(micro, botPlayer) * 50;
            score -= count_potential(micro, opponent) * 50;

            for (int j = 0; j < 9; j++) {
                int val = micro[j];
                int bonus = (j == 4) ? CENTER_BONUS : ((j == 0 || j == 2 || j == 6 || j == 8) ? CORNER_BONUS : 0);
                if (val == botPlayer) score += bonus;
                else if (val == opponent) score -= bonus;
            }
        }
    }

    return score;
}

static int minimax(int* board, int* macroBoard, int activeBlock, int depth, int alpha, int beta, int isMax, int botPlayer) {
    if (depth > 0 && (clock() > end_time)) {
        timeout_occurred = 1;
        return 0;
    }

    int winner = get_winner(macroBoard);
    if (winner != EMPTY || depth == 0) {
        return evaluate(board, macroBoard, activeBlock, isMax, botPlayer);
    }

    int opponent = (botPlayer == CROSS) ? CIRCLE : CROSS;
    int currentPlayer = isMax ? botPlayer : opponent;

    if (isMax) {
        int maxEval = -INT_MAX;
        for (int p = 0; p < 9; p++) {
            int bIdx = MOVE_PRIORITY[p];
            if (activeBlock != -1 && bIdx != activeBlock) continue;
            if (macroBoard[bIdx] != EMPTY) continue;

            for (int cp = 0; cp < 9; cp++) {
                int cIdx = MOVE_PRIORITY[cp];
                if (board[bIdx * 9 + cIdx] == EMPTY) {
                    board[bIdx * 9 + cIdx] = currentPlayer;
                    int oldMacro = macroBoard[bIdx];
                    macroBoard[bIdx] = get_winner(&board[bIdx * 9]);

                    int nextBlock = (macroBoard[cIdx] == EMPTY) ? cIdx : -1;
                    int eval = minimax(board, macroBoard, nextBlock, depth - 1, alpha, beta, 0, botPlayer);

                    board[bIdx * 9 + cIdx] = EMPTY;
                    macroBoard[bIdx] = oldMacro;

                    if (timeout_occurred) return 0;

                    if (eval > maxEval) maxEval = eval;
                    if (eval > alpha) alpha = eval;
                    if (beta <= alpha) return maxEval;
                }
            }
        }
        return (maxEval == -INT_MAX) ? evaluate(board, macroBoard, activeBlock, isMax, botPlayer) : maxEval;
    } else {
        int minEval = INT_MAX;
        for (int p = 0; p < 9; p++) {
            int bIdx = MOVE_PRIORITY[p];
            if (activeBlock != -1 && bIdx != activeBlock) continue;
            if (macroBoard[bIdx] != EMPTY) continue;

            for (int cp = 0; cp < 9; cp++) {
                int cIdx = MOVE_PRIORITY[cp];
                if (board[bIdx * 9 + cIdx] == EMPTY) {
                    board[bIdx * 9 + cIdx] = currentPlayer;
                    int oldMacro = macroBoard[bIdx];
                    macroBoard[bIdx] = get_winner(&board[bIdx * 9]);

                    int nextBlock = (macroBoard[cIdx] == EMPTY) ? cIdx : -1;
                    int eval = minimax(board, macroBoard, nextBlock, depth - 1, alpha, beta, 1, botPlayer);

                    board[bIdx * 9 + cIdx] = EMPTY;
                    macroBoard[bIdx] = oldMacro;

                    if (timeout_occurred) return 0;

                    if (eval < minEval) minEval = eval;
                    if (eval < beta) beta = eval;
                    if (beta <= alpha) return minEval;
                }
            }
        }
        return (minEval == INT_MAX) ? evaluate(board, macroBoard, activeBlock, isMax, botPlayer) : minEval;
    }
}

NativeMove get_best_move(int* board, int* macroBoard, int activeBlock, int player, int max_depth, int timeout_ms) {
    NativeMove finalBestMove = {-1, -1, -1, -1};

    timeout_occurred = 0;
    end_time = clock() + (timeout_ms * CLOCKS_PER_SEC / 1000);

    static int seeded = 0;
    if (!seeded) {
        srand(time(NULL));
        seeded = 1;
    }

    int boardCopy[81];
    int macroCopy[9];

    // Iterative deepening
    for (int current_depth = 1; current_depth <= max_depth; current_depth++) {
        NativeMove currentBestMove = {-1, -1, -1, -1};
        int bestValue = -INT_MAX;
        int count = 0;

        memcpy(boardCopy, board, sizeof(int) * 81);
        memcpy(macroCopy, macroBoard, sizeof(int) * 9);

        for (int p = 0; p < 9; p++) {
            int bIdx = MOVE_PRIORITY[p];
            if (activeBlock != -1 && bIdx != activeBlock) continue;
            if (macroCopy[bIdx] != EMPTY) continue;

            for (int cp = 0; cp < 9; cp++) {
                int cIdx = MOVE_PRIORITY[cp];
                if (boardCopy[bIdx * 9 + cIdx] == EMPTY) {
                    boardCopy[bIdx * 9 + cIdx] = player;
                    int oldMacro = macroCopy[bIdx];
                    macroCopy[bIdx] = get_winner(&boardCopy[bIdx * 9]);

                    int nextBlock = (macroCopy[cIdx] == EMPTY) ? cIdx : -1;
                    int boardVal = minimax(boardCopy, macroCopy, nextBlock, current_depth - 1, -INT_MAX, INT_MAX, 0, player);

                    boardCopy[bIdx * 9 + cIdx] = EMPTY;
                    macroCopy[bIdx] = oldMacro;

                    if (timeout_occurred) break;

                    if (boardVal > bestValue) {
                        bestValue = boardVal;
                        currentBestMove.fieldI = bIdx / 3;
                        currentBestMove.fieldJ = bIdx % 3;
                        currentBestMove.blockI = cIdx / 3;
                        currentBestMove.blockJ = cIdx % 3;
                        count = 1;
                    } else if (boardVal == bestValue) {
                        count++;
                        if (rand() % count == 0) {
                            currentBestMove.fieldI = bIdx / 3;
                            currentBestMove.fieldJ = bIdx % 3;
                            currentBestMove.blockI = cIdx / 3;
                            currentBestMove.blockJ = cIdx % 3;
                        }
                    }
                }
            }
            if (timeout_occurred) break;
        }

        if (!timeout_occurred) {
            finalBestMove = currentBestMove;
        } else {
            break;
        }

        // If we found a winning move, we can potentially stop early
        if (bestValue >= WIN_SCORE) break;
    }

    // Fallback if even depth 1 wasn't completed (unlikely but possible)
    if (finalBestMove.fieldI == -1) {
        for (int p = 0; p < 9; p++) {
            int bIdx = MOVE_PRIORITY[p];
            if (activeBlock != -1 && bIdx != activeBlock) continue;
            if (macroBoard[bIdx] != EMPTY) continue;
            for (int cp = 0; cp < 9; cp++) {
                int cIdx = MOVE_PRIORITY[cp];
                if (board[bIdx * 9 + cIdx] == EMPTY) {
                    finalBestMove.fieldI = bIdx / 3; finalBestMove.fieldJ = bIdx % 3;
                    finalBestMove.blockI = cIdx / 3; finalBestMove.blockJ = cIdx % 3;
                    return finalBestMove;
                }
            }
        }
    }

    return finalBestMove;
}
