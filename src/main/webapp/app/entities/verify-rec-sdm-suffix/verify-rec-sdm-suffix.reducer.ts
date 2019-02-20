import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVerifyRecSdmSuffix, defaultValue } from 'app/shared/model/verify-rec-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_VERIFYRECS: 'verifyRec/SEARCH_VERIFYRECS',
  FETCH_VERIFYREC_LIST: 'verifyRec/FETCH_VERIFYREC_LIST',
  FETCH_VERIFYREC: 'verifyRec/FETCH_VERIFYREC',
  CREATE_VERIFYREC: 'verifyRec/CREATE_VERIFYREC',
  UPDATE_VERIFYREC: 'verifyRec/UPDATE_VERIFYREC',
  DELETE_VERIFYREC: 'verifyRec/DELETE_VERIFYREC',
  RESET: 'verifyRec/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVerifyRecSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type VerifyRecSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: VerifyRecSdmSuffixState = initialState, action): VerifyRecSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_VERIFYRECS):
    case REQUEST(ACTION_TYPES.FETCH_VERIFYREC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VERIFYREC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VERIFYREC):
    case REQUEST(ACTION_TYPES.UPDATE_VERIFYREC):
    case REQUEST(ACTION_TYPES.DELETE_VERIFYREC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_VERIFYRECS):
    case FAILURE(ACTION_TYPES.FETCH_VERIFYREC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VERIFYREC):
    case FAILURE(ACTION_TYPES.CREATE_VERIFYREC):
    case FAILURE(ACTION_TYPES.UPDATE_VERIFYREC):
    case FAILURE(ACTION_TYPES.DELETE_VERIFYREC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_VERIFYRECS):
    case SUCCESS(ACTION_TYPES.FETCH_VERIFYREC_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_VERIFYREC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VERIFYREC):
    case SUCCESS(ACTION_TYPES.UPDATE_VERIFYREC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VERIFYREC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/verify-recs';
const apiSearchUrl = 'api/_search/verify-recs';

// Actions

export const getSearchEntities: ICrudSearchAction<IVerifyRecSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_VERIFYRECS,
  payload: axios.get<IVerifyRecSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IVerifyRecSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VERIFYREC_LIST,
    payload: axios.get<IVerifyRecSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IVerifyRecSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VERIFYREC,
    payload: axios.get<IVerifyRecSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVerifyRecSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VERIFYREC,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IVerifyRecSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VERIFYREC,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVerifyRecSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VERIFYREC,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
