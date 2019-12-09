
package acme.features.administrator.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dashboard.Dashboard;
import acme.entities.offers.Offers;
import acme.features.authenticated.offers.AuthenticatedOffersRepository;
import acme.features.authenticated.request.AuthenticatedRequestRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AdministratorDashboardRepository	repository;

	@Autowired
	AuthenticatedRequestRepository		reqRepository;

	@Autowired
	AuthenticatedOffersRepository		offerRepository;


	// AbstractShowService<Administrator, Dashboard> interface --------------

	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalAnnouncement", "totalInvestorsRecord", "totalCompanyRecords", "minRewardRequest", "maxRewardRequest", "minRewardOffers", "maxRewardOffers", "companysBySector", "sectorsOfCompanys", "inverstorsBySector",
			"sectorsOfInverstors", "mediaRequest", "mediaOffer", "stdevRequest", "stdevOffer", "jobsByFinalMode", "statusOfApplication", "applicationByStatus", "avgApplicationEmployer", "avgJobEmployer", "avgApplicationWorker");

	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		Dashboard result = new Dashboard();
		result.setTotalAnnouncement(this.getTotalAnnouncement());
		result.setTotalCompanyRecords(this.getTotalCompanyRecords());
		result.setTotalInvestorsRecord(this.getTotalInvestorsRecord());
		result.setMinRewardRequest(this.getMinRewardRequest());
		result.setMaxRewardRequest(this.getMaxRewardRequest());
		result.setMinRewardOffers(this.getMinRewardOffer());
		result.setMaxRewardOffers(this.getMaxRewardOffer());
		result.setCompanysBySector(this.getCompanysBySector());
		result.setInverstorsBySector(this.getInverstorsBySector());
		result.setSectorsOfCompanys(this.getSectorsOfCompanys());
		result.setSectorsOfInverstors(this.getSectorOfInverstors());
		result.setMediaRequest(this.getMediaRequest());
		result.setMediaOffer(this.getMediaOffer());
		result.setStdevOffer(this.getStdevOffer());
		result.setStdevRequest(this.getStdevRequest());
		result.setJobsByFinalMode(this.getJobsByFinalMode());
		result.setApplicationByStatus(this.getApplicationByStatus());
		result.setStatusOfApplication(this.getStatusOfApplication());
		result.setAvgJobEmployer(this.getMediaJobsPerEmployer());
		result.setAvgApplicationEmployer(this.getMediaApplicationPerEmployer());
		result.setAvgApplicationWorker(this.getMediaApplicationPerWorker());

		return result;
	}

	public Integer getTotalAnnouncement() {
		Integer res = this.repository.countAnnouncement();
		return res;
	}

	public Integer getTotalInvestorsRecord() {
		Integer res = this.repository.countInvestorsRecords();
		return res;
	}

	public Integer getTotalCompanyRecords() {
		Integer res = this.repository.countCompanyRecord();
		return res;
	}

	public Double getMinRewardRequest() {
		Double res = this.repository.getMinRewardRequest();
		return res;
	}

	public Double getMaxRewardRequest() {
		Double res = this.repository.getMaxRewardRequest();
		return res;
	}

	public Double getMinRewardOffer() {
		Double res = this.repository.getMinRewardOffer();
		return res;
	}

	public Double getMaxRewardOffer() {
		Double res = this.repository.getMaxRewardOffer();
		return res;
	}

	public List<Integer> getCompanysBySector() {
		List<Integer> res = this.repository.getCompanysBySector();
		return res;
	}

	public List<String> getSectorsOfCompanys() {
		List<String> res = this.repository.getSectorsOfCompanys();
		return res;
	}

	public List<Integer> getInverstorsBySector() {
		List<Integer> res = this.repository.getInverstorsBySector();
		return res;
	}

	public List<String> getSectorOfInverstors() {
		List<String> res = this.repository.getSectorOfInverstors();
		return res;
	}

	public Double getMediaRequest() {
		Double res = this.repository.getMediaRequest();
		return res;
	}

	public Double getMediaOffer() {
		return this.repository.getMediaOffer();
	}

	public Double getStdevRequest() {
		Double avg = this.getMediaRequest();
		Collection<acme.entities.request.Request> requests = this.reqRepository.findManyAll();
		Double ac = 0.0;
		for (acme.entities.request.Request r : requests) {
			Double d = (r.getReward().getAmount() - avg) * (r.getReward().getAmount() - avg);
			ac = ac + d;
		}
		Double res = Math.sqrt(ac - requests.size() - 1);
		return res;

	}

	public Double getStdevOffer() {
		Double avg = this.getMediaOffer();
		Collection<Offers> offers = this.offerRepository.findManyAll();
		Double ac = 0.0;
		for (Offers o : offers) {
			Double d0 = (o.getLowerRange().getAmount() + o.getMajorRange().getAmount()) / 2;
			Double d = (d0 - avg) * (d0 - avg);
			ac = ac + d;
		}
		Double res = Math.sqrt(ac - offers.size() - 1);
		return res;

	}

	public List<Double> getJobsByFinalMode() {
		List<Integer> result = this.repository.getJobsByFinalMode();
		Double size = result.stream().mapToDouble(x -> x).sum();
		List<Double> res = result.stream().map(x -> x.doubleValue() / size).collect(Collectors.toList());
		return res;
	}

	public List<String> getStatusOfApplication() {
		List<String> res = this.repository.getStatusOfApplication();
		return res;
	}

	public List<Double> getApplicationByStatus() {
		List<Integer> result = this.repository.getApplicationBystatus();
		Double size = result.stream().mapToDouble(x -> x).sum();
		List<Double> res = result.stream().map(x -> x.longValue() / size).collect(Collectors.toList());
		return res;

	}

	public Double getMediaJobsPerEmployer() {
		Double res = this.repository.getMediaJobsPerEmployer();
		return res;
	}

	public Double getMediaApplicationPerEmployer() {
		Double res = this.repository.getMediaApplicationPerEmployer();
		return res;
	}

	public Double getMediaApplicationPerWorker() {
		Double res = this.repository.getMediaApplicationPerWorker();
		return res;
	}

}
