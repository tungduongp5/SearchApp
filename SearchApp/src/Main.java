import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Main extends JFrame {

    private JTextArea websiteList;
    private JTextField key1, key2, key3;
    private JTextArea resultArea;

    public Main() {
        setTitle("Tìm kiếm tin trên Internet");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Tìm kiếm tin trên internet", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(250, 10, 500, 40);
        add(title);

        // Danh sách web
        JLabel lblWeb = new JLabel("Danh sách web");
        lblWeb.setBounds(50, 70, 200, 20);
        add(lblWeb);

        websiteList = new JTextArea("https://dantri.com.vn\nhttps://vnexpress.net");
        JScrollPane scrollWeb = new JScrollPane(websiteList);
        scrollWeb.setBounds(50, 100, 350, 400);
        add(scrollWeb);

        // Từ khóa
        key1 = new JTextField();
        key2 = new JTextField();
        key3 = new JTextField();

        JLabel k1 = new JLabel("Từ khóa 1");
        k1.setBounds(450, 100, 200, 20);
        add(k1);
        key1.setBounds(450, 130, 300, 30);
        add(key1);

        JLabel k2 = new JLabel("Từ khóa 2");
        k2.setBounds(450, 180, 200, 20);
        add(k2);
        key2.setBounds(450, 210, 300, 30);
        add(key2);

        JLabel k3 = new JLabel("Từ khóa 3");
        k3.setBounds(450, 260, 200, 20);
        add(k3);
        key3.setBounds(450, 290, 300, 30);
        add(key3);

        // Nút tìm kiếm
        JButton searchBtn = new JButton("Tìm kiếm");
        searchBtn.setBounds(450, 350, 200, 50);
        add(searchBtn);

        // Nút tìm giá
        JButton priceBtn = new JButton("Tìm giá sản phẩm");
        priceBtn.setBounds(450, 420, 200, 50);
        add(priceBtn);

        // Kết quả
        JLabel lblRes = new JLabel("Kết quả");
        lblRes.setBounds(800, 70, 200, 20);
        add(lblRes);

        resultArea = new JTextArea();
        JScrollPane scrollRes = new JScrollPane(resultArea);
        scrollRes.setBounds(800, 100, 350, 400);
        add(scrollRes);

        // ===== EVENT CHÍNH =====

        searchBtn.addActionListener(e -> search(false)); // tìm tin tức
        priceBtn.addActionListener(e -> search(true));   // tìm giá

        setVisible(true);
    }

    // ======================= HÀM TÌM KIẾM =========================
    private void search(boolean isPriceSearch) {
        resultArea.setText("");

        List<String> urls = Arrays.asList(websiteList.getText().split("\n"));

        String[] keys = {
                key1.getText().trim(),
                key2.getText().trim(),
                key3.getText().trim()
        };

        for (String url : urls) {
            try {
                Document doc = Jsoup.connect(url).get();
                String text = doc.text().toLowerCase();

                for (String k : keys) {
                    if (k.isEmpty()) continue;

                    String searchKey = isPriceSearch ? k + " giá" : k;

                    if (text.contains(searchKey.toLowerCase())) {
                        resultArea.append("● Tìm thấy trong: " + url + "\n");
                        resultArea.append("... " + searchKey + " ...\n\n");
                    }
                }

            } catch (Exception ex) {
                resultArea.append("Không thể truy cập: " + url + "\n\n");
            }
        }
    }

    // ======================= MAIN =========================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
