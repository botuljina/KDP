import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class GoodsImpl implements Goods, Serializable {

	private String name;
	private File file;
	private ArrayList<String> body = new ArrayList<String>();
	private int currLine = 0;
	public String toString() {
		return name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String[] getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBody(String[] body) {
		for(String line: body) {
			this.body.add(line);
		}
		
	}

	@Override
	public String readLine() {
		return body.get(currLine++);
	}

	@Override
	public void printLine(String body) {
		this.body.add(body);
	}

	@Override
	public int getNumLines() {
		return body.size();
	}

	@Override
	public void save(String name) {
		try {
			PrintWriter pw = new PrintWriter(name+".txt");
			String content = "";
			for(String line: body) {
				content+=line;
			}
			pw.write(content);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void load(String name) {
		// TODO Auto-generated method stub

	}

}
