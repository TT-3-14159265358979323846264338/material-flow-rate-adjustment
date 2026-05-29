export const allYearArray = () => {
  const startTerm = 2025;
  const yeraTerm = new Date().getFullYear() - startTerm + 1;
  return Array.from({ length: yeraTerm }, (_, i) => String(i + startTerm));
};

export const nowYearArray = () => {
  const nowYear = new Date().getFullYear();
  return [String(nowYear), String(nowYear + 1)];
};

export const monthArray = () => {
  return Array.from({ length: 12 }, (_, i) => String(i + 1));
};