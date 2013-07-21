package com.hirisun.content.web.lucene.demo1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
/*
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
*/
public class HelloLucene
{
	/**
	 * 建立索引
	 */
	/*
	public void index() 
	{
		IndexWriter indexWirter=null;
		try
		{
			//1.创建Directory
			Directory dir=new RAMDirectory();
			dir=FSDirectory.open(new File("/home/mm/桌面/demoIndexs/"));
			
			//2.创建IndexWriter
			IndexWriterConfig conf=new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)); 
			
			
			 indexWirter=new IndexWriter(dir, conf);
			 
			//3.创建Document
			Document doc=null;	
			
			
			//4.创建Document的Filed
			File file=new File("/home/mm/桌面/demo/");
			for(java.io.File f: file.listFiles())
			{
				doc=new Document();
				doc.add(new Field("content", new FileReader(f)));
				doc.add(new Field("name",f.getName(),Store.YES,Field.Index.NOT_ANALYZED));  //是否把全名存到硬盘中，是否要进行分词，文件名没有必要建立分词
				doc.add(new Field("path",f.getAbsolutePath(),Store.YES,Field.Index.NOT_ANALYZED));
				
				//5.通IndexWriter添加文档到索引中
				indexWirter.addDocument(doc);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}  finally{
			if(indexWirter!=null){
				try
				{
					indexWirter.close();
				} catch (CorruptIndexException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}		
	}
	
	*//**
	 * 搜索
	 *//*
	public void search()
	{
		
		try
		{
			//1.创建Directory
			Directory dir=FSDirectory.open(new File("/home/mm/桌面/demoIndexs/"));
		
			//2.创建IndexReader
			IndexReader reader=IndexReader.open(dir);
			
			//3.根据IndexReader创建IndexSearch
			IndexSearcher searcher=new IndexSearcher(reader);
			
			//4.创建搜索的query
			QueryParser queryParser=new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
			Query query=queryParser.parse("ext");

			//5.根据search搜索，返回Topdoc的文档
			TopDocs tds= searcher.search(query, 10); //搜索10条
			
			//6.根据topdocs获取ScoreDoc对象
			ScoreDoc[] scdocs=tds.scoreDocs;
			for(ScoreDoc sd:scdocs)
			{
				//7.根据searcher和ScoreDoc对象获取具体的document对象
				Document d=searcher.doc(sd.doc);
				
				//8.根据document对象获取所需要的词
				System.out.println(d.get("name")+"   "+d.get("path"));
				
			}
			
			//9.关闭reader
			reader.close();
			//searcher.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		HelloLucene hl=new HelloLucene();
		hl.index();
		hl.search();
	}*/
}
