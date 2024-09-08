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

	// グレースケール変換
	val gray = Mat()
	Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY)
	Imgcodecs.imwrite("round1.png", gray)

	// Gaussianブラーを適用してノイズを低減
	val blurred = Mat()
	// 引数は(input, output, カーネルサイズ, 標準偏差)
	Imgproc.GaussianBlur(gray, blurred, Size(5.0, 5.0), 1.5)
	Imgcodecs.imwrite("round2.png", blurred)

	// キャニー法によるエッジ検出
	val edges = Mat()
	// 引数は(input, output, 最小閾値, 最大閾値)
	Imgproc.Canny(blurred, edges, 100.0, 200.0)
	Imgcodecs.imwrite("round3.png", edges)
}
