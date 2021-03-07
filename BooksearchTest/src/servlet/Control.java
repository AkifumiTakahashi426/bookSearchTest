package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beans.Book;

/**
 * Servlet implementation class Control
 */
@WebServlet("/Control")
public class Control extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StringBuilder authorsBuilder;

    public Control() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    ////
    //// どうやってできてるかは聞かないでください
    //// 「onepiece」で検索して取得したtitleをコンソール上に表示しています。
    //// （199行目）
    ////
    ////
    ////
    ////

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 取得文字列の文字エンコード指定
        request.setCharacterEncoding("UTF-8");

        // JSPページを呼び出すためのRequestDispatcher
        RequestDispatcher rd = null;

        // gooleへ接続するためのURL
        URL url = null;
        HttpURLConnection con = null;

        // 検索結果データ格納用
        List<Book> list;

        // json検索の一連の流れ
        // isbnと書名の取得
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");

        // 接続URL
        String requestUrl = null;

        // ISBNが入力されていたらISBNで検索、入力されていなかったら書名等で検索
        if (isbn.equals("")) {
            // 書名等で検索
            requestUrl = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title;

            // disp.jspへ渡すデータを格納
            // request.setAttribute("key", "タイトル(" + title + ")");
        } else {
            // ISBNで検索
            requestUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;

            // disp.jspへ渡すデータを格納
            request.setAttribute("key", "ISBN(" + isbn + ")");
        }

        // Google Books APIへの接続
        try {
            // URLConnectionの作成
            url = new URL(requestUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");// GETリクエスト
            con.setReadTimeout(10000); // 10秒
            con.setConnectTimeout(10000);// 10秒
        } catch (Exception e) {
            // 例外発生時、error.jspへフォワードする
            request.setAttribute("error", e.toString());
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
            return;
        }

        // レスポンスコードの確認
        int responseCode = con.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            // 接続を切断する
            con.disconnect();

            // レスポンスコードが200以外の場合は、error.jspへフォワードする
            request.setAttribute("error", "Google Books API　へのリクエストが失敗しました。レスポンスコード：" + responseCode);
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
            return;
        }

        // 検索結果データ(レスポンス)の取得
        // ストリーム作成用
        BufferedReader responseReader = null;
        InputStreamReader isr = null;

        // レスポンス全データ取得用
        StringBuilder builder = new StringBuilder();

        // レスポンスを取得するためのストリームの作成
        isr = new InputStreamReader(con.getInputStream());
        responseReader = new BufferedReader(isr);

        // レスポンスデータを1行取得する
        String line = responseReader.readLine();
        // System.out.println(line);//test

        // nullになるまでデータを１行取り出し、builderへ追加する
        while (line != null) {
            builder.append(line);
            line = responseReader.readLine();
        }

        // 取得したデータを文字列へ変換する
        String responseString = builder.toString();
        // System.out.println(responseString);//test

        // 接続を切断する
        con.disconnect();

        // JSONオブジェクト作成用
        JSONObject jsonObject = null;

        try {
            // 取得した文字列からJSONオブジェクトを作成
            jsonObject = new JSONObject(responseString);

            // 検索データ数 totalItemsを検索結果数としています
            // 実際に検索して得られるデータは最大10個のようです
            int count = jsonObject.getInt("totalItems");

            // requestへcountを格納
            request.setAttribute("count", count);

            // 検索結果0の場合、no_result.jspへフォワードする
//                if(count == 0){
//                    rd = request.getRequestDispatcher("/no_result.jsp");
//                    rd.forward(request, response);
//                    return;
//                }

            // JSON配列itemsの取得
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            // 検索結果データの格納
            list = new ArrayList<Book>();

            // 実際に得られるデータ数
            count = jsonArray.length();

            //
            for (int i = 0; i < count; i++) {
                // 各検索結果
                JSONObject item = jsonArray.getJSONObject(i);

                // volumeInfoに関するデータの取得
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                // industryIdentifiersに関するデータの取得
                //JSONObject industryIdentifiers = volumeInfo.getJSONObject("industryIdentifiers");
                //JSONObject identifiers = volumeInfo.getJSONObject("identifiers");

             // imageLinksに関するデータの取得
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                // titleの取得
                String booktitle = volumeInfo.getString("title");

                // publisherの取得
                String publisher = null;
                try {
                    publisher = volumeInfo.getString("publisher");

                } catch (JSONException e) {
                    publisher = "未登録";
                }

                // authorsの取得
                JSONArray authors = null;
                String authorsS;
                try {
                    authors = volumeInfo.getJSONArray("authors");

                    if (jsonArray.length() > 1) {//二人以上なら
                        authorsS = authors.getString(0) + "+他";
                    }else if(jsonArray.length() == 1) {//一人なら
                        authorsS = authors.getString(0);
                    }else {//いなかったら（例外と同じ処理）
                        authorsS = "未登録";
                    }
                } catch (JSONException a) {
                    authorsS = "未登録";
                }


                // descriptionの取得
                String description = null;
                try {
                    description = volumeInfo.getString("description");

                } catch (JSONException f) {
                    description = "未登録";
                }

             // thubmnailの取得
                String thumbnail = null;
                try {
                    thumbnail = imageLinks.getString("thumbnail");

                } catch (JSONException f) {
                    thumbnail = "未登録";
                }

                String identifier = null;
                try {
                    identifier = volumeInfo.getString("industryIdentifiers");

                } catch (JSONException f) {
                    identifier = "未登録";
                }

                String selfLink = null;
                try {
                    selfLink = item.getString("selfLink");

                } catch (JSONException f) {
                    selfLink = "未登録";
                }



                int fun =3;

                String summary = "";

                boolean  alreadyRead=false;

                int have =0;

                 //検索結果データの追加
                 //ここでBookインスタンスを作成して格納している
                Book book = new Book(booktitle, publisher, authorsS, thumbnail, identifier, selfLink,
                        description, fun, summary, alreadyRead, have);

                System.out.println("title:" + book.getTitle());
                System.out.println("pub:"+book.getPublisher());
                System.out.println("auth:"+book.getAuthors());
                System.out.println("thumb:"+book.getThumbnail());
                System.out.println("isbn:"+book.getIdentifier());
                System.out.println("selflink:"+book.getSelfLink());
                System.out.println("descri:"+book.getDescription());
                System.out.println("fun:"+book.getFun());
                System.out.println("summary:"+book.getSummary());
                System.out.println("already:"+book.isAlreadyRead());
                System.out.println("have:"+book.getHave());

                list.add(book);

            }


//
//            // disp.jspへ渡すデータを格納
//            request.setAttribute("result", list);
//
        } catch (Exception e) {
            // 例外発生時、error.jspへフォワードする
            request.setAttribute("error", e.toString());
            rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
            return;
        }

        // isbn_result.jspへフォワードする
//            rd = request.getRequestDispatcher("/result.jsp");
//            rd.forward(request, response);

        // それ以外のボタン名からの場合
//        else{
//            //doGet()を呼び出す
//            doGet(request, response);
//        }

    }
}
