import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import api from "../api/axiosConfig";
import CheckIcon from "./Icons/CheckIcon";
import CrossIcon from "./Icons/CrossIcon";

const ApplicationsSection = () => {
  const isRecruiter = useSelector((state) => state.auth.isRecruiter);
  const userData = useSelector((state) => state.auth.userData);

  const [isLoading, setIsLoading] = useState(false);
  const [actionLoading, setActionLoading] = useState(false);

  const [applications, setApplications] = useState([]);
  const [pendingApplications, setPendingApplications] = useState([]);
  const [acceptedApplications, setAcceptedApplications] = useState([]);
  const [rejectedApplications, setRejectedApplications] = useState([]);

  useEffect(() => {
    const fetchApplications = async () => {
      setIsLoading(true);

      try {
        const [applicationsRes, jobsRes] = await Promise.all([
          api.get("/api/v1/applications"),
          api.get("/api/v1/jobs"),
        ]);

        const applicationsData = applicationsRes.data;
        const jobsData = jobsRes.data;

        if (isRecruiter) {
          const jobIdSet = new Set(userData?.jobIds || []);
          const recruiterApplications = applicationsData.filter((app) =>
            jobIdSet.has(app.jobId)
          );

          const formattedApplications = recruiterApplications.map((app) => {
            const job = jobsData.find((j) => j.id === app.jobId);
            return {
              ...app,
              position: job?.position ?? "Unknown Position",
            };
          });

          setApplications(formattedApplications);
          setPendingApplications(formattedApplications.filter((a) => a.status === "Pending"));
          setAcceptedApplications(formattedApplications.filter((a) => a.status === "Accepted"));
          setRejectedApplications(formattedApplications.filter((a) => a.status === "Rejected"));
        } else {
          const candidateApplications = applicationsData.filter(
            (app) => app.email === userData?.email
          );

          const formattedApplications = candidateApplications.map((app) => {
            const job = jobsData.find((j) => j.id === app.jobId);
            return {
              ...app,
              position: job?.position ?? "Unknown",
              company: job?.company ?? "Unknown",
              location: job?.location ?? "Unknown",
            };
          });

          setApplications(formattedApplications);
          console.log("formattedApplications =>", formattedApplications);
          
        }
      } catch (error) {
        console.error("Failed to fetch applications/jobs:", error);
      }

      setIsLoading(false);
    };

    if (userData) {
      fetchApplications();
    }
  }, [isRecruiter, userData]);

  const handleStatusUpdate = async (item, newStatus) => {
    setActionLoading(true);
    try {
      const response = await api.post(`/api/v1/applications/${item.id}`, newStatus);
      if (response.status === 200) {
        const updatedItem = { ...item, status: newStatus };

        setPendingApplications(pendingApplications.filter((app) => app.id !== item.id));

        if (newStatus === "Accepted") {
          setAcceptedApplications([...acceptedApplications, updatedItem]);
        } else if (newStatus === "Rejected") {
          setRejectedApplications([...rejectedApplications, updatedItem]);
        }
      }
    } catch (error) {
      console.error(`Failed to ${newStatus} application:`, error);
    }
    setActionLoading(false);
  };

  const renderCandidateApplicationDetailsLeftSide = (item) => (
    <div className="p-6 w-4/5">
      <div className="mb-6">
        <p className="font-semibold">
          {item.name}
          <span className="ml-10 text-sm opacity-80">{item.qualification}</span>
        </p>
        <p>{item.position}</p>
      </div>
      <div>
        <p className="opacity-80">{item.email}</p>
        <p className="opacity-80">{item.phone}</p>
        <p className="my-4">
          {item.skills?.map((skill, idx) => (
            <span key={idx} className="mr-2 py-1 px-2 bg-slate-700 text-xs border rounded-md">
              {skill}
            </span>
          ))}
        </p>
        {item.resumeLink && (
          <a href={item.resumeLink} target="_blank" rel="noopener noreferrer" className="underline">
            Resume
          </a>
        )}
      </div>
    </div>
  );

  const renderApplicationsByStatus = (title, list, color, showActions = false) => (
    <div className="mt-16">
      <h2 className="text-white text-2xl font-bold">{title}</h2>
      <div className="my-8 flex flex-col gap-6 text-white">
        {isLoading ? (
          <p className="my-10 text-lg font-semibold">Loading...</p>
        ) : list.length > 0 ? (
          list.map((item) => (
            <div
              key={item.id}
              className={`flex justify-between divide-x-2 border ${
                color && `border-${color}-500`
              } rounded-lg`}
            >
              {renderCandidateApplicationDetailsLeftSide(item)}
              <div className="px-6 w-1/5 flex flex-col justify-evenly items-center">
                {showActions ? (
                  <>
                    <button
                      onClick={() => handleStatusUpdate(item, "Accepted")}
                      disabled={actionLoading}
                      className={`py-3 px-8 bg-green-600 hover:opacity-70 rounded-lg font-semibold transition-opacity ${
                        actionLoading && "opacity-30"
                      }`}
                    >
                      Accept
                      <CheckIcon width="1.5em" height="1.5em" />
                    </button>
                    <button
                      onClick={() => handleStatusUpdate(item, "Rejected")}
                      disabled={actionLoading}
                      className={`py-3 px-8 bg-red-600 hover:opacity-70 rounded-lg font-semibold transition-opacity ${
                        actionLoading && "opacity-30"
                      }`}
                    >
                      Reject
                      <CrossIcon width="1.5em" height="1.5em" />
                    </button>
                  </>
                ) : (
                  <p className={`font-bold text-lg text-${color}-500`}>{title.split(" ")[0]}</p>
                )}
              </div>
            </div>
          ))
        ) : (
          <p className="my-10 text-lg font-semibold">No {title.toLowerCase()}</p>
        )}
      </div>
    </div>
  );

  if (isRecruiter) {
    return (
      <div className="my-10">
        {renderApplicationsByStatus("Pending Applications", pendingApplications, "", true)}
        {acceptedApplications.length > 0 &&
          renderApplicationsByStatus("Accepted Applications", acceptedApplications, "green")}
        {rejectedApplications.length > 0 &&
          renderApplicationsByStatus("Rejected Applications", rejectedApplications, "red")}
      </div>
    );
  }

  return (
    <div className="my-10">
      <h2 className="text-white text-2xl font-bold">Your Applications</h2>
      <div className="p-4 my-4 border rounded-lg text-white">
        <div className="flex flex-col gap-2 divide-y divide-white/40">
          {isLoading ? (
            <p className="my-10 text-lg font-semibold">Loading...</p>
          ) : applications.length > 0 ? (
            applications.map((item) => (
              <div key={item.id} className="flex justify-between items-center py-3 px-4">
                <div>
                  <p className="font-semibold">{item.position}</p>
                  <p>
                    {item.company}
                    <span className="ml-4 text-sm opacity-80">@ {item.location}</span>
                  </p>
                </div>
                <div>
                  <p
                    className={`${
                      item.status === "Accepted"
                        ? "text-green-500"
                        : item.status === "Rejected"
                        ? "text-red-500"
                        : ""
                    }`}
                  >
                    {item.status}
                  </p>
                </div>
              </div>
            ))
          ) : (
            <p>You have not applied to any jobs!</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ApplicationsSection;
