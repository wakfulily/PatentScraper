package scraper.core.scrapers;

public class CitationCountScraperGiven extends ElementCountScraper {

	private static final String READABLE_NAME = "Given citation count";

	public CitationCountScraperGiven(PageScraper pageScraper) {
		super(READABLE_NAME, pageScraper);
	}

	@Override
	public String[] getPropertyNames() {
		return new String[] {"given citation count"};
	}

	@Override
	protected String getElementSelector() {
		return "tr[itemprop=backwardReferencesOrig]";
	}

}
