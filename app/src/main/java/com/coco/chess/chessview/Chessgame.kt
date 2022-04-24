package com.coco.chess.chessview

import com.coco.chess.R
import java.lang.Math.abs


object Chessgame {
    var xo = true
    private var piece = mutableSetOf<Chesspiece>()

    init {
        reset()
    }

    private fun canKnightMove(from: Square, to: Square): Boolean {
        return abs(from.col - to.col) == 2 && abs(from.row - to.row) == 1 ||
                abs(from.col - to.col) == 1 && abs(from.row - to.row) == 2
    }

    private fun canRookMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && isClearVerticallyBetween(from, to) ||
                from.row == to.row && isClearHorizontallyBetween(from, to)) {
            return true
        }
        return false
    }

    private fun isClearVerticallyBetween(from: Square, to: Square): Boolean {
        if (from.col != to.col) return false
        val gap = abs(from.row - to.row) - 1
        if (gap == 0) return true
        for (i in 1..gap) {
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (pieceAt1(Square(from.col, nextRow)) != null) {
                return false
            }
        }
        return true
    }

    private fun ispawnClearVerticallyBetween(from: Square, to: Square): Boolean {
        if (from.col != to.col) return false
        if (pieceAt1(Square(from.col, to.row)) === null) {
                return true
            }

        return false
    }



    private fun isClearHorizontallyBetween(from: Square, to: Square): Boolean {
        if (from.row != to.row) return false
        val gap = abs(from.col - to.col) - 1
        if (gap == 0) return true
        for (i in 1..gap) {
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            if (pieceAt1(Square(nextCol, from.row)) != null) {
                return false
            }
        }
        return true
    }

    private fun isClearDiagonally(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) != abs(from.row - to.row)) return false
        val gap = abs(from.col - to.col) - 1
        for (i in 1..gap) {
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (pieceAt(nextCol, nextRow) != null) {
                return false
            }
        }
        return true
    }
     private fun ispawnClearDiagonally(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) != abs(from.row - to.row)) return false
        val gap = abs(from.col - to.col)-1
        for (i in 1..gap) {
            val nextCol = if (to.col > from.col) from.col + i else from.col - i
            val nextRow = if (to.row > from.row) from.row + i else from.row - i
            if (pieceAt(nextCol, nextRow) != null) {
                return false
            }
        }
        return true
    }

    private fun canBishopMove(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) == abs(from.row - to.row)) {
            return isClearDiagonally(from, to)
        }
        return false
    }

    private fun canQueenMove(from: Square, to: Square): Boolean {
        return canRookMove(from, to) || canBishopMove(from, to)
    }

    private fun canKingMove(from: Square, to: Square): Boolean {
        if (canQueenMove(from, to)) {
            val deltaCol = abs(from.col - to.col)
            val deltaRow = abs(from.row - to.row)
            return deltaCol == 1 && deltaRow == 1 || deltaCol + deltaRow == 1
        }
        return false
    }
    private fun canpawnmove1(from: Square,to: Square):Boolean{
        if (pieceAt(from.col,from.row)!!.player == Player.WHITE ){
            if (from.col == to.col&&from.row == 1 && from.row < to.row ) {
                if (from.row == 1 && from.row < to.row) {
                    return to.row == 2 || to.row == 3
                }
            }else if (abs(from.row - to.row) == abs(from.col - to.col)&&from.row == to.row -1&& pieceAt(to.col,to.row) != null){
                return ispawnClearDiagonally(from, to)
              }
             else if(from.row == to.row -1){
                return ispawnClearVerticallyBetween(from, to)
               }

        } else{
            if (from.col == to.col && from.row == 6 && pieceAt(to.col,to.row) == null&& pieceAt(to.col,to.row+1) == null){
                if (to.row ==4)return true

            }else if (abs(from.row - to.row) == abs(from.col - to.col) && from.row == to.row +1&& pieceAt(to.col,to.row) != null){
                return ispawnClearDiagonally(from, to)
            }
           else if(from.row == to.row +1){
                return ispawnClearVerticallyBetween(from, to)
            }

        }
        return false
    }

    private fun canPawnMove(from: Square, to: Square): Boolean {
        if (from.col == to.col ) {
            if (from.row == 1 && from.row < to.row) {
                return to.row == 2 || to.row == 3
            } else if (from.row == 6 && from.row < to.row) {
                return to.row == 5 || to.row == 4

            }
        }
        return false
    }


    fun canMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row) {
            return false
        }
        val movingPiece = pieceAt1(from) ?: return false
        return when (movingPiece.chessman) {
            Chessman.KNIGHT -> canKnightMove(from, to)
            Chessman.ROOK -> canRookMove(from, to)
            Chessman.BISHOP -> canBishopMove(from, to)
            Chessman.QUEEN -> canQueenMove(from, to)
            Chessman.KING -> canKingMove(from, to)
            Chessman.PAWN -> canpawnmove1(from, to)
        }
    }

    fun clear() {
        piece.clear()
    }

    fun addPiece(piece1: Chesspiece) {
        piece.add(piece1)
    }

    fun reset() {
        clear()
        for (i in 0 until 2) {
            addPiece(Chesspiece(0 + i * 7, 0, Player.WHITE, Chessman.ROOK, R.drawable.rook_white))
            addPiece(Chesspiece(0 + i * 7, 7, Player.BLACK, Chessman.ROOK, R.drawable.rook_black))

            addPiece(Chesspiece(1 + i * 5, 0, Player.WHITE, Chessman.KNIGHT, R.drawable.knight_white))
            addPiece(Chesspiece(1 + i * 5, 7, Player.BLACK, Chessman.KNIGHT, R.drawable.knight_black))

            addPiece(Chesspiece(2 + i * 3, 0, Player.WHITE, Chessman.BISHOP, R.drawable.bishop_white))
            addPiece(Chesspiece(2 + i * 3, 7, Player.BLACK, Chessman.BISHOP, R.drawable.bishop_black))
        }

        for (i in 0 until 8) {
            addPiece(Chesspiece(i, 1, Player.WHITE, Chessman.PAWN, R.drawable.pawn_white))
            addPiece(Chesspiece(i, 6, Player.BLACK, Chessman.PAWN, R.drawable.pawn_black))
        }

        addPiece(Chesspiece(3, 0, Player.WHITE, Chessman.QUEEN, R.drawable.queen_white))
        addPiece(Chesspiece(3, 7, Player.BLACK, Chessman.QUEEN, R.drawable.queen_black))
        addPiece(Chesspiece(4, 0, Player.WHITE, Chessman.KING, R.drawable.king_white))
        addPiece(Chesspiece(4, 7, Player.BLACK, Chessman.KING, R.drawable.king_black))
    }

    fun pieceAt1(square: Square): Chesspiece? {
        return pieceAt(square.col, square.row)
    }


     fun pieceAt(col: Int, row: Int): Chesspiece? {
        for (piece1 in piece) {
            if (col == piece1.col && row == piece1.row) {
                return piece1
            }
        }
        return null
    }

    fun movePiece(from: Square, to: Square) {
        if (canMove(from, to)) {
            movePiece(from.col, from.row, to.col, to.row)

        }

    }

    private fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if (fromCol == toCol && fromRow == toRow) return
        val movingPiece = pieceAt(fromCol, fromRow) ?: return
        pieceAt(toCol, toRow)?.let {
            if (it.player == movingPiece.player) {
                xo = false
                return
            }
            xo = true
            piece.remove(it)
            }


        piece.remove(movingPiece)
        addPiece(movingPiece.copy(col = toCol, row = toRow)).let {


        }
    }


}
