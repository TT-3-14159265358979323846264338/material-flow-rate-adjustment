export const onlyHalfWidthAlphanumericCharacters = (value: string): string => {
  return value.replace(/[^A-Za-z0-9]/g, "");
};

export const onlyHalfWidthCharacters = (value: string): string => {
  return value.replace(/[^A-Za-z0-9!@#$%^&*()_+\-=\[\]{};':",./<>?]/g, "");
};