package xmlparse;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DOM4JTest {
    private static ArrayList<Book> bookList = new ArrayList<Book>();
    /**
     * @param args
     */
    public static void main(String[] args) {
        // 解析books.xml文件
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(new File("./src/xmlparse/books.xml"));
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            
           
            while (it.hasNext()) {
                System.out.println("=====开始遍历某一本书=====");
                Element book = (Element) it.next();
                // 获取book的属性名以及 属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："+ attr.getValue());
                    set(bookList,attr.getName(),attr.getValue());
                }
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    System.out.println("节点名：" + bookChild.getName() + "--节点值：" + bookChild.getStringValue());
                    set(bookList,bookChild.getName(),bookChild.getStringValue());
                }
                System.out.println("=====结束遍历某一本书=====");
                
            }
            System.out.println(bookList.toString());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
	private static void set(ArrayList<Book> bookList2, String name, String value) {
		Book  book=new Book();
		if("id".equals(name)){book.setId(value);}
		if("name".equals(name)){book.setName(value);}
		if("author".equals(name)){book.setAuthor(value);}
		if("year".equals(name)){book.setYear(value);}
		if("price".equals(name)){book.setPrice(value);}
		if("Language".equals(name)){book.setLanguage(value);}
		bookList2.add(book);
	}
}

