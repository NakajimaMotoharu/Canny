import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc

fun main() {
	// OpenCVのネイティブライブラリをロード
	// 注意: PATHにネイティブライブラリが追加されていることが必須
	System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

	// 画像を読み込み
	val src = Imgcodecs.imread("input.png")

	// 画像が見つからなかった場合
	if (src.empty()) {
		println("Error: Image not found.")
		return
	}

	// サイズ変更
	val resized = Mat()
	Imgproc.resize(src, resized, Size(512.0, 512.0))
	Imgcodecs.imwrite("round1.png", resized)

	// グレースケール変換
	val gray = Mat()
	Imgproc.cvtColor(resized, gray, Imgproc.COLOR_BGR2GRAY)
	Imgcodecs.imwrite("round2.png", gray)

	// Gaussianブラーを適用してノイズを低減
	val blurred = Mat()
	// 引数は(input, output, カーネルサイズ, 標準偏差)
	// 省略してあるが、標準偏差(Y)や画像端をどう処理するかも指定可能
	Imgproc.GaussianBlur(gray, blurred, Size(5.0, 5.0), 1.5)
	Imgcodecs.imwrite("round3.png", blurred)

	// キャニー法によるエッジ検出
	val edges = Mat()
	// 引数は(input, output, 最小閾値, 最大閾値)
	// 省略してあるが、ソベルフィルタのサイズや勾配計算の際の距離の扱いも指定可能
	Imgproc.Canny(blurred, edges, 100.0, 200.0)
	Imgcodecs.imwrite("round4.png", edges)
}
