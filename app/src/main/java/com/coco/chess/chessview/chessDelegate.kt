package com.coco.chess.chessview

interface chessDelegate {
    fun pieceAt(square: Square) : Chesspiece?
    fun movePiece(from: Square, to: Square)
}