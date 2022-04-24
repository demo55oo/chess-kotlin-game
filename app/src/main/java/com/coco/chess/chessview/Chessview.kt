package com.coco.chess.chessview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.coco.chess.R
import com.coco.chess.chessview.Chessgame.pieceAt
import kotlin.math.min

class Chessview(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
      private val paint = Paint()
      private val paint1 = Paint()
    private val lightColor = Color.parseColor("#efdcd5")
    private val darkColor = Color.parseColor("#A98B52")
    private val ww = Color.parseColor("#e2ed1c")
    val  wow = ArrayList<Color>()

    private val scalefactor = .9f
    private var originX = 20f
    private var originY = 200f
    private var cellSide = 130f
    private val imageids = setOf(
            R.drawable.bishop_black,
            R.drawable.bishop_white,
            R.drawable.king_black,
            R.drawable.king_white,
            R.drawable.queen_black,
           R.drawable.queen_white,
           R.drawable.rook_black,
           R.drawable.rook_white,
            R.drawable.knight_black,
        R.drawable.knight_white,
       R.drawable.pawn_black,
       R.drawable.pawn_white,
    )
    private val bitmaps = mutableMapOf<Int, Bitmap>()
    private var movingPieceBitmap: Bitmap? = null
    private var movingPiece: Chesspiece? = null
     var chesedeligate: chessDelegate? = null
    private var clicked  :Int = 0
    init {
        loadBitmaps()
    }
    var turn = Player.WHITE
 private var fromCol  = -1
 private var fromRow  = -1
    private var movingPieceX = -1f
    private var movingPieceY = -1f
    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        canvas.let {
            val chessbiardside = min(it.width,it.height)*scalefactor
            cellSide = chessbiardside/8f
            originX = (width - chessbiardside) / 2f
            originY = (height - chessbiardside) / 2f

        }

        drawChessboard(canvas!!)
        drawPieces(canvas!!)
        /*if (clicked==1){
            drawoutlinesquare(canvas,fromCol,fromRow)
        }*/
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

                fromCol = ((event.x - originX) / cellSide).toInt()
                fromRow = 7 - ((event.y - originY) / cellSide).toInt()

                chesedeligate?.pieceAt(Square(fromCol, fromRow))?.let {
                    var x = it.player
                    if(x == turn){
                        movingPiece = it
                        movingPieceBitmap = bitmaps[it.resID]
                    }

                }
            }
            MotionEvent.ACTION_MOVE -> {
                movingPieceX = event.x
                movingPieceY = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                val col = ((event.x - originX) / cellSide).toInt()
                val row = 7 - ((event.y - originY) / cellSide).toInt()
                if (fromCol != col || fromRow != row) {
                chesedeligate?.pieceAt(Square(fromCol, fromRow))?.let {
                    var x = it.player
                    if (x == turn) {
                        chesedeligate?.movePiece(Square(fromCol, fromRow), Square(col, row))
                        val wwwww = pieceAt(fromCol, fromRow)
                        pieceAt(col, row)?.let {


                            if (fromCol !== col || fromRow !== row ) {
                                if (turn == Player.WHITE) {
                                    turn = Player.BLACK
                                } else {
                                    turn = Player.WHITE
                                }


                            }
                        }

                    }




                }
                }
                movingPiece = null
                movingPieceBitmap = null
                invalidate()

            }
        }
        return true
    }

    private fun drawPieces(canvas: Canvas) {
        for (row in 0 until 8)
            for (col in 0 until 8)
                chesedeligate?.pieceAt(Square(col, row))?.let { piece ->
                    if (piece != movingPiece) {
                        drawPieceAt(canvas, col, row, piece.resID)
                    }
                }

        movingPieceBitmap?.let {
            canvas.drawBitmap(it, null, RectF(movingPieceX - cellSide/2, movingPieceY - cellSide/2,movingPieceX + cellSide/2,movingPieceY + cellSide/2), paint)
        }
    }

    private fun drawPieceAt(canvas: Canvas, col: Int, row: Int, resID: Int) =
            canvas.drawBitmap(bitmaps[resID]!!, null, RectF(originX + col * cellSide,originY + (7 - row) * cellSide,originX + (col + 1) * cellSide,originY + ((7 - row) + 1) * cellSide), paint)

    private fun loadBitmaps() =
            imageids.forEach { imgResID ->
                bitmaps[imgResID] = BitmapFactory.decodeResource(resources, imgResID)
            }

    private fun drawChessboard(canvas: Canvas) {
        for (row in 0 until 8)
            for (col in 0 until 8)
                drawSquareAt(canvas, col, row, (col + row) % 2 == 1)
    }
    private fun drawSquareAt(canvas: Canvas, col: Int, row: Int, isDark: Boolean) {
        paint.color = if (isDark) darkColor else lightColor
        canvas.drawRect(originX + col * cellSide, originY + row * cellSide, originX + (col + 1)* cellSide, originY + (row + 1) * cellSide, paint)
    }

    private fun drawoutlinesquare(canvas: Canvas, col: Int, row: Int) {
        paint1.style = Paint.Style.STROKE
        paint1.color = ww
        canvas.drawRect(originX + col * cellSide, originY + row * cellSide, originX + (col +1  )* cellSide, originY + (row + 1) * cellSide, paint1)
    }
   }