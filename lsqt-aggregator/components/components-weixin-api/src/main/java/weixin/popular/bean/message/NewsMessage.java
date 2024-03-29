package weixin.popular.bean.message;

import java.util.List;

/**
 * 图文消息 
 * 图文消息条数限制在10条以内，注意，如果图文数超过10，则将会无响应。
 *
 */
public class NewsMessage extends Message {

	public NewsMessage(String touser) {
		super(touser,"news");
	}

	private News news;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public static class News {

		private List<Article> articles;

		public List<Article> getArticles() {
			return articles;
		}

		public void setArticles(List<Article> articles) {
			this.articles = articles;
		}
	}

	public static class Article {
		private String title;
		private String description;
		private String url;
		private String picurl;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getPicurl() {
			return picurl;
		}

		public void setPicurl(String picurl) {
			this.picurl = picurl;
		}

	}
}
